# first line comment
x, y, z = 1, 2, 3
fun_no_args()
fun_args(x, ...)
fun_table_arg {}
fun_string_arg[[hello]]
do end
while false do end
repeat until true
for x = 1, 10 do end
for x = 1, 10, 1 do end
for x, y in pairs(table) do end
function foo(x, y) end
function bar(x, ...) end
local function foo() end
local function bar(...) end
local x, y, z = 1, 2, 3
if true then end
if true then else end
if true then elseif false then elseif false then end
if true then elseif false then elseif false then else end
do break end
do return end
x = y    --var
x[1] = y --indirect table access
x.y = z  --direct table access
foo:bar()
t = { ["x"] = 1, y = 2, 3, {} }
function foo(...) local args = ... end
