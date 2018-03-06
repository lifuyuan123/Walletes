#include <jni.h>
#include <string>

extern "C"
//
///**
// *这个key是和服务器之间通信的秘钥
// */
//const char* AUTH_KEY = "服务器通信秘钥";
//
///**
// * 发布的app 合法签名的签名字符串
// * signature[0].toCharString()得到
// *
// */
//const char* RELEASE_SIGN = "这是合法的签名字符串";
//
///**
// * 发布的app 合法签名的HashCode
// * signature[0].hashCode()得到
// */
//const int RELEASE_SIGN_HASHCODE = 123456789;

JNIEXPORT jstring JNICALL
Java_com_wallet_bo_wallets_Utils_RequestHelpr_keyFromJNI(
        JNIEnv *env, jclass jclazz, jobject contextObject) {
    jclass native_class = env->GetObjectClass(contextObject);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(contextObject, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(contextObject);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(contextObject, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray)signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);

    //第一种方式--检查签名字符串的方式
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char*)env->GetStringUTFChars(str,0);


    if(strcmp(c_msg,"3082036b30820253a00302010202041652e4bb300d06092a864886f70d01010b05003066310f300d060355040613067869616f6d6f310f300d060355040813067869616f6d6f310f300d060355040713067869616f6d6f310f300d060355040a13067869616f6d6f310f300d060355040b13067869616f6d6f310f300d060355040313067869616f6d6f301e170d3137303932303031303333385a170d3432303931343031303333385a3066310f300d060355040613067869616f6d6f310f300d060355040813067869616f6d6f310f300d060355040713067869616f6d6f310f300d060355040a13067869616f6d6f310f300d060355040b13067869616f6d6f310f300d060355040313067869616f6d6f30820122300d06092a864886f70d01010105000382010f003082010a0282010100a962ee6fbd91dcb4a7b01c9008820cb016a8ab30c846edfdbbaa856fe8ec77ab0f034a4d3b8e3e0bf0f66a4845c8217dfceb62d75235b7365aa37ee57525aa56be37e00f806c92b82c2a913aa036fc0c93eb524df0bec8e52bfdc7a76e24c18ff917c9043b1bcedbb5260cc221950f5e86259e44617c0f5c39e96e06beab00631fea238f63da9e258acec35fcfb3f2a06c9aee04fd084b7df03a0c5f24a77f185d1b201af15cd8284117c9d0c2bb31b39e6f0ed485c47d94627f433ff0831b1ea416443545937410d94bf3ffc69b08abb0e7f1284ee6ca7fe431d7618405ad299fad56cd46d47bc4ee6a7054dd833313e44bd75437a347a2f8ccab39591273a10203010001a321301f301d0603551d0e0416041443d3a3676de6e946105c61104124da30afce1050300d06092a864886f70d01010b050003820101006f505ada858bcea3634136aa64fbe58b4d481fda7dd5ccd7f79a144246d904dacb4e1f176b1dd51bd89a124840b6fa3fdbcb6a0b2712df8272d699af22b0de45a1ad45cae0b941ad485a653da6f8ea3b8d2ca833112180e9018c4a3dcfecf7f901cfe22354b728cb2f9c519bf57ef9da94fa0ac37f7078c8d525770c993e78f786d97872af639948fe09ad9e1aecfafc6f0b11bd371e5083453f4cc1c6486325ec77862e603ec623afd6c81e7eb21f349d826f8501c6174e7acffb3f5081ee3a0a43a2683b741ebf2a31df829633ed92a0828f41a49a25eff1320792578e5bc9a62ec7444776904de92e0d6f0eb6b2c63932da5b6e92b6549f335eb34bb3a0e8")==0)//签名一致  返回合法的 key，否则返回错误
    {
        return (env)->NewStringUTF("SioXHEFuMVUPuJKLuHqZTlzWWFlfRQhe");//服务器通信密匙
    }else
    {
        return (env)->NewStringUTF("error");
    }

    //第二种方式--检查签名的hashCode的方式
    /*
    jmethodID int_hashcode = env->GetMethodID(signature_clazz, "hashCode", "()I");
    jint hashCode = env->CallIntMethod(signature_obj, int_hashcode);
    if(hashCode == RELEASE_SIGN_HASHCODE)
    {
        return (env)->NewStringUTF(AUTH_KEY);

    }else{
        return (env)->NewStringUTF("error");
    }
     */
}
