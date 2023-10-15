import { useContext, useState, createContext  } from 'react';
import { executeBasicAuthenticationService, executeJwtAuthenticationService } from '../api/AuthenticationApiService';
import { apiClient } from '../api/ApiClient';

// 1:Create a Context
export const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

// 2:Share the cerated context with other components
export default function AuthProvider({children}) {
    
    //Put some state in the context
    //const [number, setNumber] = useState(10);

    const [isAuthenticated, setAuthenticated] = useState(false);

    const [username, setUsername] = useState(null);

    const [token, setToken] = useState(null);

    //setInterval( () => setNumber(number + 1), 10000);

    //const valueToBeShared = {number, isAuthenticated, setAuthenticated}

    // function login(username, password) {
    //     if(username === 'in28minutes' && password === 'dummy'){
    //         setAuthenticated(true);
    //         setUsername(username);
    //         return true;
    //     }else {
    //         setAuthenticated(false);
    //         setUsername(null);
    //         return false;
    //     }
    // }

    // async function login(username, password) {  //async 메서드로 만드는 이유는 axios가 Promise이기 때문이다 즉 비동기로 처리됨

    //     const baToken = 'Basic ' + window.btoa(username + ":" + password);  //base64 인코딩 함수 
        

    //     try {
    //         const response = await executeBasicAuthenticationService(baToken);  //실행이 되는걸 기다릴수 있다.
                     
    //         if(response.status == 200){
    //             setAuthenticated(true);
    //             setUsername(username);
    //             setToken(baToken);

    //             apiClient.interceptors.request.use( // 요청 인터셉터 등록: 요청이 보내기 전에 실행
    //                 (config) => {
    //                     console.log('intercepting and adding a token');
    //                     config.headers.Authorization = baToken;  // 요청 헤더에 Authorization 필드를 추가하고 값을 baToken으로 설정
    //                     return config;  // 수정된 요청 설정 반환
    //                 } 
    //             )

    //             return true;
    //         }else {
    //             logout();
    //             return false;
    //         }
    //     } catch(error) {
    //         logout();
    //         return false;
    //     }
        
    // }

    async function login(username, password) {  

        try {
            const response = await executeJwtAuthenticationService(username, password); 
                     
            if(response.status == 200){
                const jwtToken = 'Bearer ' + response.data.token;
                setAuthenticated(true);
                setUsername(username);
                setToken(jwtToken);

                apiClient.interceptors.request.use( 
                    (config) => {
                        console.log('intercepting and adding a token');
                        config.headers.Authorization = jwtToken; 
                        return config; 
                    } 
                )

                return true;
            }else {
                logout();
                return false;
            }
        } catch(error) {
            logout();
            return false;
        }
        
    }

    function logout() {
        setAuthenticated(false);
        setUsername(null);
        setToken(null);
    }

    return (
        <AuthContext.Provider value={{isAuthenticated, login, logout, username, token}}>  
            {children}
        </AuthContext.Provider>
    );
} 