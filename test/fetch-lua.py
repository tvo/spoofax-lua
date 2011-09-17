import binascii, gzip, os
from rapid.rapid import Rapid, set_spring_dir

path = os.path.abspath('tmp')
dest = os.path.abspath('parse')

if not os.path.exists(dest):
	os.mkdir(dest)

set_spring_dir(path)
rapid = Rapid()

def process(p):
	print 'processing', p.name

	print '  downloading sdp package file'
	files = [f for f in p.files if f.name.endswith('.lua')]

	print '  downloading lua files'
	p.download_files(files)

	print '  unpacking lua files'
	for f in files:
		cont = gzip.GzipFile(f.pool_path).read()
		md5 = binascii.hexlify(f.md5)
		with file(os.path.join(dest, md5) + '.lua', 'w') as w:
			w.write(cont.replace('\r', '').rstrip())
			w.write('\n')
			w.write('-- package name : ' + p.name + '\n')
			w.write('-- package hex  : ' + p.hex + '\n')
			w.write('-- package tags : ' + ' '.join(p.tags) + '\n')
			w.write('-- file path    : ' + f.name + '\n')
			w.write('-- file md5     : ' + md5 + '\n')

for p in rapid.packages:
	if any(t for t in p.tags if t.endswith('latest')):
		process(p)

print 'done.'
