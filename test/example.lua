module example

// Example "Lua" program (see syntax/Lua.sdf for the grammar)

entity User {
  name     : String
  password : String
  homepage : URL
}

entity BlogPosting {
  poster : User
  body   : String
}

entity URL {
  location : String
}