package com.wallet.bo.wallets.lianlianpay;

/**
 * author:ggband
 * date:2017/8/30 11:53
 * email:ggband520@163.com
 * desc:
 */

public class EnvConstants {

    private EnvConstants() {
    }

    /**
     * TODO 商户号，商户MD5 key 配置。本测试Demo里的“PARTNER”；强烈建议将私钥配置到服务器上，以免泄露。“MD5_KEY”字段均为测试字段。正式接入需要填写商户自己的字段
     */
    public static final String PARTNER_PREAUTH = "201504071000272504"; // 短信

    public static final String MD5_KEY_PREAUTH = "201504071000272504_test_20150417";

    public static final String PARTNER = "201708090000781882";

    public static final String MD5_KEY = "201708090000781882_sahdisa_20170809";

    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
    // public static final String RSA_PRIVATE =
    // "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    public static final String RSA_PRIVATE = "MIICXAIBAAKBgQCx+3E9WO+p0dn88KpkW07FvI4FHGGzaFP2uBlf3155UOZKggYpIuDCabuK2OtpY0mhNWjNNwZOVWJz6aNlyDIK4J5eCZq1UtlDqB+GvmfwwIYuWVBvN6a/OiC9KAWkgdeB3jSCoWpKE7excBNT+OVpj/EEvhcQ3Gf+i4/k6bKRKwIDAQABAoGALKFyygFtR9ZZpXXsJHJJFIitzFZLoaN9upZ7tnDaze0dcn2cceBPIDFHWBABKFI/em9cMmmj6Sy8HXjvAmzrLyd22T7uiJblp86cxnTF9dRq8U5udhYHeOgbjllnnhHqsvYT6872M5QkSfAryQvfqzdCHgOTB91cahW9SOAO9xECQQDszHzHn3vQx5sxC1YapZu0xVyBM1AJLJmHMf6mgPlXhrT7Rkp4OM4z3NVxB+ATarW7XEkmzHsfkA1GF8drsJTtAkEAwGoHjPGK/LDhQaOcUXD6MTh1esb4aszJZLn95Ce93ZGur2rvLq0pFygvRCaR13LNpLTNcpMXVgcmmy+roZbTdwJANE7znXE4e6UsPghADADbRXJ3fb7lBjHI9Flx5DwHWHNUVVFwr9/0hPZaW+6ebAude3bmXeC7rfw3Qm2MStil/QJAHl3deTH5tG2Z70kJ95Rz9+aoK8tRE1HkiuALoMFg5qICRq8CtoQfkwcFhqTrqWv15oOeVPG/4hTY0+8PED6EewJBAJ/Jh/SCLNU/PcU1T2NK4iucJp/YzrTAJ2XHIaWbuqLfoTbMM/XcCvi5hW2lDfVcoLOyP8DSuqOydMMT6a4s8so=\n";

    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";


}
