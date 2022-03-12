import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANJo3vwr3mrVaIFsFwMGCoNhJ2rwyEY1\n' +
  '0ho2wOJX0DdJrOBrenWzvaVi+i1zeWaNr4UJE1tT7K7dGhMsR3VUfrECAwEAAQ=='

const privateKey = 'MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEA0mje/CveatVogWwX\n' +
  'AwYKg2EnavDIRjXSGjbA4lfQN0ms4Gt6dbO9pWL6LXN5Zo2vhQkTW1Psrt0aEyxH\n' +
  'dVR+sQIDAQABAkEApM3HejTSLVOiW1zSWWft7CG4wiZ+d5/IRQgF5ENrryD1ER+f\n' +
  'ogsPGfM3yWPLOjNwn08CjYXSF8JVTBJXs2K2kQIhAPxm2mTC29/Q+1PoZi6LilRT\n' +
  'CTNA5KKSbzkAkF/IXnVHAiEA1WjEP8MHLcdOmvnO2a1lun7xZ0WpUmG/lVDj5oeq\n' +
  'SEcCIQCUUhykeZ2buML4W+kwEPAI/YK6i7fPkR2q8Znlr2lSoQIhALKVOzSIRaef\n' +
  'Uz4IsXoiyjAoLmbXOnv/5ExVJoExIePdAiEA5ZU59rrAa0rL6jgleilZIMFuBhsD\n' +
  'pRpcdNhf7K7rajY='

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

