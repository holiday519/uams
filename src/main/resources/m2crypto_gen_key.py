from M2Crypto import RSA,BIO
from base64 import b64encode,b64decode
import os,sys


def m2c_key(arg1, arg2):
    try:
        rsa = RSA.gen_key(1024, 3, lambda *agr:None)
        pub_bio = BIO.MemoryBuffer()
        priv_bio = BIO.MemoryBuffer()

        rsa.save_pub_key_bio(pub_bio)
        rsa.save_key_bio(priv_bio, None)
        
        pub_file = arg1 
       # pub_file = arg1 + 'pub.pem'
        private_file = arg2 
       # private_file = arg2 + 'private.pem'
        open(pub_file,'w').write(pub_bio.read_all())
        open(private_file,'w').write(priv_bio.read_all())

        pub_key = RSA.load_pub_key(pub_file)
        priv_key = RSA.load_key(private_file)

        message = 'opzoon'
  
        encrypted = pub_key.public_encrypt(message, RSA.pkcs1_padding)
        decrypted = priv_key.private_decrypt(encrypted, RSA.pkcs1_padding)

        if decrypted==message:
            #os.popen("rm -rf m2crypto_gen_key.py")
            pass
        else:
            os.popen("rm -rf *.pem")
            print "Key generation failed,please try again!"
            raise Exception("Key generation failed,please try again!")
    except Exception,e:
        os.popen("rm -rf *.pem")
        raise Exception("Key generation failed,please try again!")


if __name__ == '__main__':
    
    arg1 = sys.argv[1]
    arg2 = sys.argv[2]

    m2c_key(arg1,arg2)
