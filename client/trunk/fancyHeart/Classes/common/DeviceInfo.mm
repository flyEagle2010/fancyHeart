//
//  DeviceInfo.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-5-4.
//
//

#include "DeviceInfo.h"
#include "KeychainItemWrapper.h"
const char* DeviceInfo::getUUID(){
    
    /** 初始化一个保存用户帐号的KeychainItemWrapper */
    /*
    KeychainItemWrapper *wrapper = [[KeychainItemWrapper alloc] initWithIdentifier:@"Account Number"
                                                                accessGroup:@"YOUR_APP_ID_HERE.com.yourcompany.AppIdentifier"];
    NSString* password=[wrapper objectForKey:(id)kSecValueData];
    //keychain search
    if(password == nil){
        CFUUIDRef theUUID = CFUUIDCreate(NULL);
        CFStringRef guid = CFUUIDCreateString(NULL, theUUID);
        CFRelease(theUUID);
        NSString *uuidString = [((NSString *)guid) stringByReplacingOccurrencesOfString:@"-" withString:@""];
        CFRelease(guid);
        
        //保存数据
        [wrapper setObject:@"com.dy.fancyHeart" forKey:(id)kSecAttrAccount];
        [wrapper setObject:uuidString forKey:(id)kSecValueData];
    }
       
    password=[wrapper objectForKey:(id)kSecValueData];
    
    return [password UTF8String];
    */
       
    CFUUIDRef theUUID = CFUUIDCreate(NULL);
    CFStringRef guid = CFUUIDCreateString(NULL, theUUID);
    CFRelease(theUUID);
    NSString *uuidString = [((NSString *)guid) stringByReplacingOccurrencesOfString:@"-" withString:@""];
    CFRelease(guid);
    return [uuidString UTF8String];
}

const char* DeviceInfo::getSystem(){
    NSString *systemVersion = [[UIDevice currentDevice]systemVersion];
    std::string str([systemVersion cStringUsingEncoding:NSUTF8StringEncoding]);
    return str.c_str();
}