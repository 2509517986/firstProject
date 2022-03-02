export const PRESET_BIT_TOTAL = 255 // 预置位总数
export const CRUISE_TOTAL = 16 // 巡航总数
export const INSPECTION_TOTAL = 4 // 巡检总数

import { getDicts } from "@/api/system/dict/data";
// 下载图片
export const downloadImg = function (imgSrc, name){
    let image = new Image()
    image.setAttribute('crossOrigin', 'anonymous')
    image.onload = function () {
        let canvas = document.createElement('canvas')
        canvas.width = image.width;
        canvas.height = image.height;

        let context = canvas.getContext('2d');
        context.drawImage(image, 0, 0, image.width, image.height);
        let url = canvas.toDataURL('image/png');
        let a = document.createElement('a');
        let event = new MouseEvent('click');
        a.download = name || 'img';
        a.href = url;
        a.dispatchEvent(event);
    }
    image.src = imgSrc;
 }
 //数据字典数据重组
 export const zidian = function(str) {
    let arry = str.replace(/\s/g,"").split("|"),obj = {};
    if(arry.length>0) {
        for(let key of arry) {
            let oj = {},arry=[];
            obj[key] = {obj:{},arry:[]}
            getDicts(key).then(response => {
                if(response.code == 200) {
                    let data = response.data;
                    if(data && data.length>0) {
                        let datatext = [];
                        for(let item of data) {
                            oj[item.dictValue] = item.dictLabel;
                            arry.push({text:item.dictLabel,value:item.dictValue});
                        }
                    }
                }
            }); 
            obj[key].obj = oj;
            obj[key].arry= arry;
        }
    }    
    return obj;
 }
 export const _systemCheck = function () {
    let returnValue=[],u = navigator.userAgent;
    returnValue['android']= u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    returnValue['ios']= !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    returnValue['weixin']=!!u.match(/MicroMessenger/i)
    return returnValue
}
// 判断是否为json
export const isJson = function(value) {
if (typeof value == 'string') {
    try {
    const obj = JSON.parse(value)
    return !!(typeof obj == 'object' && obj)
    } catch (e) {
    return false
    }
}
}
//全清localStorage
export const clearStorage = function () {
    localStorage.clear()
}

//获取本地存储类内容
export const getKey = function (key) {
    let value = localStorage.getItem(key)
    return value ? value : ''
}

//获取本地存储类内容并转对象
export const getLocalStorage = (key) => {
    const value = getKey(key)
    if(isJson(value)) {
        return JSON.parse(value)
    } else {
        return false
    }
}

//设置本地存储类内容
export const setKey = function (key, value) {
    localStorage.setItem(key, value)
}

//清除本地存储类内容
export const removeKey = function (key) {
    localStorage.setItem(key, '')
}
//删除本地存储类内容
export const removeItem = function (key) {
    localStorage.removeItem(key)
}
//获取sessionstorage存储类内容
export const clearSession = function () {
    sessionStorage.clear()
}
//获取sessionstorage存储类内容
export const getSessionKey = function (key) {
    let value = sessionStorage.getItem(key)
    return value ? value : ''
}

//设置sessionstorage存储类内容
export const settSessionKey = function (key, value) {
    sessionStorage.setItem(key, value)
}

//清除sessionstorage存储类内容
export const removetSessionKey = function (key) {
    sessionStorage.removeItem(key);
}
// 实时参数-收藏初始化-开关
export const fav_init_toggle = function(name) {
    if(getLocalStorage("configMenu")) {
        if(getLocalStorage("configMenu").indexOf(name) == -1) {
            return 0;
        } else {
            return 1;
        }
    } else {
        return 0;
    }
}
// 实时参数-取消收藏 | 收藏功能
export const addfav = function(name) {
    let configMenu = [];configMenu = getLocalStorage("configMenu")?getLocalStorage("configMenu"):[];
    if(configMenu.indexOf(name) == -1) {
        configMenu.push(name);
        setKey("configMenu",JSON.stringify(configMenu));
        return 1;
    } else {
        let index = configMenu.indexOf(name);
        configMenu.splice(index,1);
        setKey("configMenu",JSON.stringify(configMenu));
        return 0;
    }
}