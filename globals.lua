g = {}
for k,_ in pairs(_G) do
  g[#g+1] = '"'..k..'", '
end
for i=1,#g,5 do
  s = ''
  for j=1,math.min(#g-i,5) do s = s..g[i+j] end
  print(s)
end
