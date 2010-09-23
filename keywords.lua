local keywords = {
  "and"    , "break"  , "do"   , "else"     , "elseif" ,
  "end"    , "false"  , "for"  , "function" , "if"     ,
  "in"     , "local"  , "nil"  , "not"      , "or"     ,
  "repeat" , "return" , "then" , "true"     , "until"  , "while"
}

function print_completions()
  for _, kw in ipairs(keywords) do
    print('completion keyword: "' .. kw .. '"')
  end
end

print_completions()
