local aa
aa = 0
function x(aa)
  aa = 0
  function y(aa)
    aa = 0
    x()
  end
  aa = 0
end
