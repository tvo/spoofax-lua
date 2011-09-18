/*
 * Spoofax-Lua: a Lua Eclipse plugin based on Spoofax
 * Copyright (C) 2011  Tobi Vollebregt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lua;

import java.io.InputStream;
import java.io.IOException;
import org.eclipse.imp.parser.IParseController;
import org.strategoxt.imp.runtime.Environment;
import org.strategoxt.imp.runtime.dynamicloading.BadDescriptorException;
import org.strategoxt.imp.runtime.dynamicloading.Descriptor;
import org.strategoxt.imp.runtime.dynamicloading.DescriptorFactory;
import org.strategoxt.imp.runtime.dynamicloading.DynamicParseController;

public class LuaParseController extends DynamicParseController 
{ 
  public static final String LANGUAGE = new String("Lua");

  private static final String TABLE = "/include/" + LANGUAGE + ".tbl";

  private static final String DESCRIPTOR = "/include/" + LANGUAGE + ".packed.esv";

  private static volatile Descriptor descriptor;

  private static Throwable notLoadingCause;

  public static synchronized Descriptor getDescriptor()
  { 
    if(notLoadingCause != null)
      throw new RuntimeException(notLoadingCause);
    if(descriptor == null)
      createDescriptor();
    return descriptor;
  }

  private static void createDescriptor()
  { 
    try
    { 
      InputStream descriptorStream = LuaParseController.class.getResourceAsStream(DESCRIPTOR);
      InputStream table = LuaParseController.class.getResourceAsStream(TABLE);
      if(descriptorStream == null)
        throw new BadDescriptorException("Could not load descriptor file from " + DESCRIPTOR + " (not found in plugin: " + getPluginLocation() + ")");
      if(table == null)
        throw new BadDescriptorException("Could not load parse table from " + TABLE + " (not found in plugin: " + getPluginLocation() + ")");
      descriptor = DescriptorFactory.load(descriptorStream, table, null);
      descriptor.setAttachmentProvider(LuaParseController.class);
    }
    catch(BadDescriptorException exc)
    { 
      notLoadingCause = exc;
      Environment.logException("Bad descriptor for " + LANGUAGE + " plugin", exc);
      throw new RuntimeException("Bad descriptor for " + LANGUAGE + " plugin", exc);
    }
    catch(IOException exc)
    { 
      notLoadingCause = exc;
      Environment.logException("I/O problem loading descriptor for " + LANGUAGE + " plugin", exc);
      throw new RuntimeException("I/O problem loading descriptor for " + LANGUAGE + " plugin", exc);
    }
  }

  private static String getPluginLocation()
  { 
    return LuaParseController.class.getProtectionDomain().getCodeSource().getLocation().getFile();
  }

  @Override public IParseController getWrapped()
  { 
    if(!isInitialized())
    { 
      if(notLoadingCause != null)
        throw new RuntimeException(notLoadingCause);
      try
      { 
        initialize(this, getDescriptor().getLanguage());
      }
      catch(BadDescriptorException exc)
      { 
        notLoadingCause = exc;
        throw new RuntimeException(exc);
      }
    }
    return super.getWrapped();
  }

  @Override protected void setNotLoadingCause(Throwable value)
  { 
    notLoadingCause = value;
    super.setNotLoadingCause(value);
  }
}