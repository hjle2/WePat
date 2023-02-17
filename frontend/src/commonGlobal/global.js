// [global > global.js 설명]
// 1. 모든 컴포넌트가 공유할 수 있는 글로벌 영역 입니다


// [설정 수행 실시]
export default {

    install(Vue) {

        // [로컬 스토리지 데이터 저장]
        Vue.config.globalProperties.$setLocalStoage = function(key, value){
            localStorage.setItem(key, value);
        },


        // [로컬 스토리지 저장 값 호출]
        Vue.config.globalProperties.$getLocalStoage = function(key){
            return localStorage.getItem(key);
        },


        // [로컬 스토리지 특정 값 삭제]
        Vue.config.globalProperties.$delItemLocalStoage = function(key){
            localStorage.removeItem(key);
        },


        // [로컬 스토리지 전체 데이터 삭제]
        Vue.config.globalProperties.$delAllLocalStoage = function(){
            localStorage.clear();
        }
    }
}