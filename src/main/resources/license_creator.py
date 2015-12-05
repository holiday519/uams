#!/usr/bin/python

try:
    import json
except ImportError:
    import simplejson as json
import os
import sys, getopt
import tempfile
from uuid import uuid4
from M2Crypto import BIO, RSA, EVP
import M2Crypto.util as m2util
from base64 import b64encode, b64decode
#from twisted.internet import defer, ssl
#from ozcommon.crypto import get_key_from_cert,M2CryptoKey
#from ozcommon.crypto import generate_keys
#from ozcommon.crypto import KeyczarKey

opzooncloud_keys_debug = ['-----BEGIN CERTIFICATE-----\nMIICvTCCAaUCEAxYSOaxfEMPulEfRmu0RZowDQYJKoZIhvcNAQEEBQAwHTEOMAwG\nA1UECxMFc2l0ZTExCzAJBgNVBAMTAmNhMB4XDTEzMDQwOTA3MjExNVoXDTIzMDQw\nNzA3MjExNVowHTEOMAwGA1UECxMFc2l0ZTExCzAJBgNVBAMTAmNhMIIBIjANBgkq\nhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh9VDO2evgz1yisZGhRiTnYxgTMMNkE3I\nVXvF9+AR4n7QTCM84hb1uzcc+dGo7FOYy6F6wHVs2Z0SfJz+RcJ+Ecgg+JOgQINL\no9yo9pDaRTKm8gX34Fewh9zzNrjyHB3GsCo1AYfyOxc2nWbd0YFe0olIl0a/vuUJ\nt4txwdujtckXZiqyO5og3djNqZbJzrqfilv6K98sts/RSxboqJDw1BicZ9TlcpPr\nFlRibcL2iqGrbCfCtywTkgijIen2nzduVmuWy/36fNZOb6JeVgIWpT+mK1T0CqgF\nlhJOwaOuC+YvT5t7TpLh/AfCMUS1+5sVsKaETwSRmPZ94496S65CfwIDAQABMA0G\nCSqGSIb3DQEBBAUAA4IBAQAX4630c6d194u669DDS4mkikqK/jYmuTccNQAWG4EK\n1gssnFcMdcg9lZnP2VA0zQ95wujaGK9XFA5TyWgF44P2kpJ/RaE2lF9IcMG2/fBh\nYqkN3fW3sn2iaNiDqoQvTrRlHrUOaWcAvBG92/0W0zKWpswW8wfwlgej7U/oyF5R\n1G8nxNzMhBxnlnGq3lRr95rCAGtGBhmCTWtL27jTV7GVAGc/QiQiTVcbsbZTY+lT\nabfGNZq7IQXcCgwDkmPPLCeOOz5nQhvW3eXHVjnsGGPtsc6o7dhTS1vvnInxU823\nHCiyBbu3VbzHFpDC5yv4m/tjLlm04TJ0T24+pYiRivgv\n-----END CERTIFICATE-----\n']
opzooncloud_keys = ['-----BEGIN PUBLIC KEY-----\nMIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDJ5f1gQIEEnxZ8cSvxUwjgLgxQ\nq2kNas4l+SJgePeMaQ+3k/zlhqc1uKd3LKtjM6BXrdQSia5zzaHNRYpW5M+FgzCU\nUxt2u5hofPPT3E5rZMX+hWRUoH3Kd96Si5ifCCQuoG8pTsHmoAaLE6KT95cLWml5\nKd4Xm6Em4Kw4qBV0jwIBAw==\n-----END PUBLIC KEY-----\n']
opzooncloud_signs = ['-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCH1UM7Z6+DPXKK\nxkaFGJOdjGBMww2QTchVe8X34BHiftBMIzziFvW7Nxz50ajsU5jLoXrAdWzZnRJ8\nnP5Fwn4RyCD4k6BAg0uj3Kj2kNpFMqbyBffgV7CH3PM2uPIcHcawKjUBh/I7Fzad\nZt3RgV7SiUiXRr++5Qm3i3HB26O1yRdmKrI7miDd2M2plsnOup+KW/or3yy2z9FL\nFuiokPDUGJxn1OVyk+sWVGJtwvaKoatsJ8K3LBOSCKMh6fafN25Wa5bL/fp81k5v\nol5WAhalP6YrVPQKqAWWEk7Bo64L5i9Pm3tOkuH8B8IxRLX7mxWwpoRPBJGY9n3j\nj3pLrkJ/AgMBAAECggEAW8NVAiLPzUeLw/ii13N89ehJflIfLad+eVF+sjArpOmq\n6mS70rlUFL/s0VA0YEMxbA+RD8fEW1bjkE+274WvCXA75fJFRo/sX6zIcJn2+tx3\nL/WcOXDED2TS4dsbpKbw/T3mRTyEBitud/NwUwKk/nJEGf5vtxSf9eE8Xox1Em7e\n+2dUBlZfWbMz7cRlpulWO3XSwGROyVRnr1vQpqiv7cwd/833nlLUR5cikAF+Ks1e\noHoKla1biQnH0lV7eSFlnB/KnV1JpepquSv1VScVVo/T30q7PCNcMzNvMjf6hdVy\n+ezgKfic+hG3nFCx5ZfMVGPNr6MTEF54ukKB6+E2MQKBgQCUy7naTeLeRxBy1kd3\nBmNXfap7Pc7xuCM1eXiHpJlTIO0n2+DbTfH1bM+edlQ8zUkPynyW+QudCI8pzZJj\nRNr87KoSauhCvacQEq/lYNoR3kqb81ShsrVbxd3lnoQ24ps6VizCMl/D+rqD8tiM\n9Yi3DnfclfO4QSMrq8ZDX3UhvQKBgQDpsqedk6l3NNRTdwNNfXe2fAjaCFrHbD1U\npEEui8J5dFQsrLoSJNmLw9erX8b+xu+Gp5jnlYNdq97vQB686WVAMrCg/ourE69T\nnugA+4+BU8f1pp9RHNnDadzDHxSFdLc0xFRyyE6xVLkSLAKiY9BrKw6fGWGBm4mU\nnb3hY/0S6wKBgQDhkGboWVYAkSU/SmgNq0SZBi8lA1tbkMXBpKilXJBg2j9Ev5L5\nYEf59xV7e/5+e1VGCw4yuMUK7JFJLAJ/JGduB38kZYw5m85mT8Ju8GAa29rs595u\nVAJ78vn5+027YmRSsg0wnNYXo0bem0wPoXfReMWsGrtadd3dbEOSAQiL8QKBgHgB\ny9a3BKgTlr+X9TGZNFwnHW2ijxrDUZpaawyzg3nZT4zbOQKHXgN8jAMZGp2jUC7t\nOAknuecgNR8ZDt0Ddr+RPSX3lXXmsiwzdixBLgBFUdVvrq1vjfgDoREGBUqi/2FQ\niW7i4/wTZ4TuQxuFGIvTnnaJdnUADBa3xwMlpTndAoGAIxegdXHPxxJDtcw+38At\n0jrZNADv3ZgtLwYGVBvP11Ukos5EZAhdmodBTRBYPJJi9gj7eL+ctbcSP5MOnmFt\nTw9lPq8+zKqeBnbZZrG1GGkOcZeksELxd6OInDrYxE7pcbsnhOtbCXolHCEtKHeJ\njAdu7IGWGZawPbCIe3uOi/A=\n-----END PRIVATE KEY-----']
#opzooncloud_keys = [ decode_key(key) for key in opzooncloud_keys ]

org_code = ('{"days": 45,"cores": 2000,"nodes": 100,"cloud_uuid": "None","license_uuid": "338b0154-dd77-4051-bac7-ff2d9a085850","start_time": "2011-10-14T08:21:47Z"}')
code = ('{"data": {"days": 45,"cores": 2000,"nodes": 100,"cloud_uuid": "None","license_uuid": "338b0154-dd77-4051-bac7-ff2d9a085850","start_time": "2011-10-14T08:21:47Z"}, "signature": "AKLakYoZ47j7+bXDOpv6nfdyN8N+R0mzJUO6NoyRCiBhwV/reZG6RHDaUfoVQ31JqsOQMw2PleieoUueiP9jsIBW+IIACEsRCttEoDYZgXskh5FDgN9Xsqqnb3XljXa6L8+Tm22cFwgRQMb6fspUKmYNCD5W8nykqwGSNsIfMOAP7wuVUFRAkqfukY0s0KNkve19ZcsIyoHN2bnggbDu/FEVn945N7Mwi5cKHwnhNjlm9L9fVhi23m6VeGBnPHwH0hya25+DwRq7162xDbm+bKa3JW2DszMGbhlYMHCLAWi9cSvVwk92k19jT+yFUXgsb7s7I3hQ/LUmBP6DECyZ6Sqi1IOt"}')

UNLIMITED_FEATURES = u"unlimited"


class KeyczarKey(object):

    def __init__(self, meta, key):
        self.meta = meta
        self.key = key



    def sign(self, data):
        signer = kc.Signer(MemoryReader(self.meta, self.key))
        return signer.Sign(data)



    def verify(self, data, signature):
        try:
            verifier = kc.Verifier(MemoryReader(self.meta, self.key))
            return verifier.Verify(data, signature)
        except KeyNotFoundError:
            return False



    def encrypt(self, data):
        encrypter = kc.Encrypter(MemoryReader(self.meta, self.key))
        return encrypter.Encrypt(data)



    def decrypt(self, data):
        try:
            crypter = kc.Crypter(MemoryReader(self.meta, self.key))
            return crypter.Decrypt(data)
        except KeyNotFoundError,e:
            raise CannotDecryptError(str(e))



    def exportPublic(self):
        key = keys.ReadKey(keydata.KeyMetadata.Read(self.meta).type, self.key)
        param = dict([ (x,
         getattr(key.key, x)) for x in key.key.keydata if hasattr(key.key, x) ])
        return util.ExportRsaX509(param)




    def exportPublicPEM(self):
        return ("%s\n%s\n%s" % (PUBLIC_PEM_START,
         linesof(self.exportPublic()),
         PUBLIC_PEM_END))

    def exportPrivate(self):
        key = keys.ReadKey(keydata.KeyMetadata.Read(self.meta).type, self.key)
        param = dict([ (x,getattr(key.key, x)) for x in key.key.keydata if hasattr(key.key, x) ])
        param["dp"] = (param["d"] % (param["q"] - 1))
        param["dq"] = (param["d"] % (param["p"] - 1))
        param["invq"] = param["u"]
        return util.ExportRsaPkcs8(param)


    def exportPrivatePEM(self):
        return ("%s\n%s\n%s" % (PRIVATE_PEM_START,
         linesof(self.exportPrivate()),
         PRIVATE_PEM_END))



    def createCACertificate(self, dn, serial = None, expires = 31536000):
        key = ssl.KeyPair.load(self.exportPrivatePEM(), OpenSSL.crypto.FILETYPE_PEM)
        if isinstance(dn, str):
            dn = distinguished_name(CN=dn)
        cr = key.certificateRequest(dn)
        serial = (serial or int(time.time()))
        return cert.dumpPEM()



    def signCertificate(self, cr, issuer, serial = None, expires = 31536000):
        key = ssl.KeyPair.load(self.exportPrivatePEM(), OpenSSL.crypto.FILETYPE_PEM)
        if isinstance(issuer, str):
            issuer = distinguished_name(CN=issuer)
        serial = (serial or int(time.time()))
        cert = ssl.Certificate.load(key.signCertificateRequest(issuer, cr, VerifyDN, serial, OpenSSL.crypto.FILETYPE_PEM, secondsToExpiry=expires))
        return cert.dumpPEM()



    def createCertificateRequest(self, dn):
        key = ssl.KeyPair.load(self.exportPrivatePEM(), OpenSSL.crypto.FILETYPE_PEM)
        if isinstance(dn, str):
            dn = distinguished_name(CN=dn)
        cr = key.certificateRequest(dn)
        payload = b64encode(cr)
        return ("%s\n%s\n%s" % (CR_PEM_START,
         linesof(payload),
         CR_PEM_END))

class M2CryptoKey():

    def __init__(self, key, pub_type = True):
        self.key = key
        self.pub_type = pub_type



    def sign(self, data):
        if not self.pub_type:
            m=EVP.MessageDigest("sha1")
            m.update(data)
            digest=m.final()
            key=RSA.load_key_string(self.key, m2util.no_passphrase_callback)
            sign = key.sign(digest, "sha1")
            return OpenSSLEncode(sign)


    def verify(self, data, signature):
        if self.pub_type:
            signature = OpenSSLDecode(signature)
            m=EVP.MessageDigest("sha1")
            m.update(data)
            digest=m.final()
            mb=BIO.MemoryBuffer(self.key)
            cert=RSA.load_pub_key_bio(mb)
            try:
                cert.verify(digest, signature, "sha1")
                return True
            except:
                return False
        else:
            return False



    def encrypt(self, data):
        if self.pub_type:
            mb=BIO.MemoryBuffer(self.key)
            pub_key=RSA.load_pub_key_bio(mb)
            return pub_key.public_encrypt(data, RSA.pkcs1_padding)



    def decrypt(self, data):
        if not self.pub_type:
            bio = BIO.MemoryBuffer(self.key);
            pri_key = RSA.load_key_bio(bio);
            return pri_key.private_decrypt(data,RSA.pkcs1_padding)

def OpenSSLEncode(s):
    " OpenSSL compatible replacement for keyczar encode "
    return b64encode(str(s))

def OpenSSLDecode(s):
    " OpenSSL compatible replacement for keyczar decode "
    return b64decode(str(s))

def generate_keys(config, purpose = "sign", name = None):
    privkeys = tempfile.mkdtemp()
    pubkeys = tempfile.mkdtemp()
    kz.main(["create",
     ("--location=%s" % privkeys),
     ("--name=%s" % (name or config.dget("opzooncloud.realm"))),
     ("--purpose=%s" % purpose),
     "--asymmetric=rsa"])
    kz.main(["addkey",
     ("--location=%s" % privkeys),
     "--status=primary"])
    kz.main(["pubkey",
     ("--location=%s" % privkeys),
     ("--destination=%s" % pubkeys)])
    keyreader = FileReader(privkeys)
    private = {}
    private["meta"] = keyreader.GetMetadata()
    private["key"] = keyreader.GetKey(1)
    private = json.dumps(private)
    keyreader = FileReader(pubkeys)
    public = {}
    public["meta"] = keyreader.GetMetadata()
    public["key"] = keyreader.GetKey(1)
    public = json.dumps(public)
    rmtree(privkeys, True)
    rmtree(pubkeys, True)
    return (public,
     private)


def get_key_from_cert(cert):
    fd, certfile = tempfile.mkstemp()
    fd2, keyfile = tempfile.mkstemp()
    os.write(fd, cert)
    os.close(fd)
    os.system('openssl x509 -in %s -pubkey -noout > %s' % (certfile, keyfile))
    os.remove(certfile)
    key = os.read(fd2, 1048576)
    os.close(fd2)
    os.remove(keyfile)
    return _make_key(cert, key)


def _make_key(cert, key):
    return M2CryptoKey(key,True)

def site_fingerprint():
    cert = open("/etc/opzooncloud/cacert.pem").read()
    certificate = ssl.Certificate.loadPEM(cert)
    fingerprint = certificate.digest("sha1")
    return fingerprint

def verify(code,pub_file):
    data = code.pop("data")
    #print code
    #print data
    signature = code.pop("signature")
    if 'debug' in data.keys():
        print data.keys()
        keyczarHandler = get_key_from_cert(opzooncloud_keys_debug[0])
    else:
        opzoon_keys = open(pub_file)
        opzooncloud_keys = []
        opzooncloud_keys.append(opzoon_keys.read())
        keyczarHandler = M2CryptoKey(opzooncloud_keys[0],True)
    if keyczarHandler.verify(str(data), signature):
        #print "Licnese verify OK, decode with:"
        #print str(data)
        pass
    else:
        print "Licnese verify FAIL"

def create_code(days=45, cores=2000, cpus=2000, nodes=100, cloud_uuid=u"None", features=None, key_file=None, debug=False):
    from datetime import datetime, timedelta
    code = {}
    data = {}
    # by wangxiaofan , add unicode
    data[u'days'] = days
    data[u'cores'] = cores
    data[u'physical_cpus'] = cpus
    data[u'nodes'] = nodes
    #data[u'key'] = key_file
    #data[u'debug'] = debug
    if features:
        data[u'features'] = features
    data[u'cloud_uuid'] = unicode(cloud_uuid)
    data[u'license_uuid'] =  unicode(str(uuid4()))
    now = datetime.utcnow()
    data[u'start_time'] = unicode(now.strftime("%Y-%m-%dT%H:%M:%SZ"))
    code['data'] = data
    if debug:
        data[u'debug'] = debug
        keyczarSignHandler = M2CryptoKey(opzooncloud_signs[0],False)
    else:
        keyczarSignHandler = M2CryptoKey(key_file,False)
    code['signature'] = keyczarSignHandler.sign(str(data))
    #print "================== KEY BEGIN =================="
    print json.dumps(code)
    #print "================== KEY END   =================="
    return code

def print_help():
    print "Usage:"
    print "license_creator <-d days> <-c cores> <-p physical_cpus> <-n nodes> <-k private key file> <-g public key file> [-u cloud_uuid] <-o create file path> [-f feature1,feature12,...]"

if __name__ == '__main__':
    #site_fingerprint = site_fingerprint()
    #print site_fingerprint
    cloud_uuid = None
    debug = False
    args = sys.argv[1:]
    
     
    
    #import pdb;pdb.set_trace()
    try:
        opts, ags = getopt.getopt(args, "d:c:p:n:u:f:k:g:i:o:")
        #print '$$$$$$$$$$$$$$$$$$$'
    except:
        print_help()
        sys.exit(1)
    
    if len(ags) > 0:
        print len(ags)
        print_help()
        sys.exit(1)
        
    opts = dict(opts)

    for k in ["-d","-c","-n","-p"]:
        if (k not in opts) or (not opts[k].isdigit()):
            print_help()
            sys.exit(1)
    if "-i" in opts:
        debug = True
        if opts["-i"] != "DeBuG":
            print_help()
            sys.exit(1)

    elif ("-k" in opts) and ("-g" in opts):
        if not os.path.isfile(opts["-k"]):
            print_help()
            sys.exit(1)
    else:
        print_help()
        sys.exit(1)

    features = opts.get("-f")
    if features:
        features = [unicode(f) for f in features.split(",")]
        if UNLIMITED_FEATURES in features:
            features = [UNLIMITED_FEATURES]
    if "-k" in opts:
        key_file = opts.get("-k")
        key_file = open(key_file,'r')
        key_file = key_file.read()
    else:
        key_file = None
    code = create_code(days=float(opts["-d"]),
                       cores=float(opts["-c"]),
                       cpus=float(opts["-p"]),
                       nodes=float(opts["-n"]),
                       cloud_uuid=opts.get("-u", u"None"),
                       key_file=key_file,
                       debug = debug,
                       features=features)
    
    #print ""
    pub_file = opts.get("-g")
    if "-o" in opts:
        path_file = opts.get("-o");
    with open(path_file,"w") as f:
        json.dump(code, f)
    #print "rrrrrrrrrrrrrrrrrrrrrrrrr"
    #print pub_file
    verify(code,pub_file)
