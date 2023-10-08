# SpringStudyWithUdemy
28minutes강의를 듣고 공부한 것을 정리


9.Spring Boot와 React로 JAVA 풀스택 애플리케이션 구축하기
  npm 명령어
- npm start: 개발모드에서 애플리케이션을 실행. 개발을 빠르게 변경하고 브라우저에서 피드백을 볼 수 있게 해 줌
- npm test: Java에 Junit이 있는 것처럼 리액트와 JavaScript 코드에서도 npm test를 이용해 유닛 단위 테스트가 가능하다
- npm run build: 배포 가능 유닛을 프로덕션으로 만드는걸 도와줌. 최소화 되고 성능도 최적화된다
- npm install: 특정 종속성을 설치 한다

  리액트 앱 폴더 구조
- README.md: 설명 문서, 명령어 설명 등등
- package.json: 종속성을 정의하는 파일 /Maven의 pom.xml과 유사
- node_modules: 모든 종속성이 다운로드 되는 폴더
- 리액트 기본값
  1. public/index.html: 브라우저에서 가장 먼저 로드되는 파일. body태그에 <div id="root"></div>가 있는데 index.js에서 컴포넌트로 채운다 
  2. src/index.js: 리액트 앱을 초기화 하고 앱 컴포넌트를 로드 (앱 컴포넌트란 실제 화면에 표시되는 컴포넌트)
  3. src/index.css: 전체 애플리케이션의 CSS를 담고 있다.
  4. src/App.css: 특정 컴포넌트 CSS를 담을 수 있다.
  5. src/App.test.js: 단위 테스트 코드

- 함수 컴포넌트 생성 방법
  function FirstComponent() {
    return(
      <div className='FirstComponent'>First Component</div>
    );
  }
  이와 같이 함수 컴포넌트를 만들고 App컴포넌트에 추가한다.

- 클래스 컴포넌트 생성 방법
  import { Component } from 'react'; 컴포넌트를 임포트 한뒤/ 중괄호 default export가 아니라는걸 알려준다.
  class ThirdComponent extends Component {
    render() {
      return(
        <div className='ThirdComponent'>Third Component</div>
      )
    }
  }
  이와 같이 컴포넌트를 상속받는 클래스 컴포넌트를 만들고 render합수에 text값을 넣는다.

- State: 각 컴포넌트에는 State라는게 존재한다. State란 특정 컴포넌트에 대한 데이터나 정보일 뿐 원래는 클래스 컴포넌트에만 존재 하였다. 다만 리액트 16.8부터 Hooks가 함수 컴포넌트에도 State를 쉽게 추가 할 수 있게 해줬다
  따라서 클래스 컴포넌트를 사용할 필요가 없어졌다.
  
- JSX는 HTML보다 엄격하며 닫는 태그가 있어야 하고, 최상위 태그가 하나만 있어야 한다.

- return값에 괄호가 있는 이유: return으로 JSX를 반환할 때 return하는 줄에 반드시 첫번째 요소가 있어야 한다. 하지만 괄호를 사용하여 코드의 가독성을 좋게 할 수 있다.

- 컴포넌트의 이름은 반드시 대문자로 시작하며, HTML태그는 소문자로 시작한다.: 어떤 JSX를 쓰더라도 HTML태그와 명확히 구분이 가능하다.

- JSX에서는 class대신 className으로 사용해 CSS를 적용한다.

- 리액트 코드 정리방법
  1. 각 컴포넌트를 각 파일, 각 모듈에 집어넣는다.  / 각 컴포넌트를 사용하려면 임포트를 해야한다.
  2. 각 컴포넌트를 관리하는 컴포넌트를 만들어 사용한다.
 




10. Counter 예제를 통해 React 컴포넌트 살펴보기
- JSX에서 함수 호출시 onClick={incrementCounterFunction}과 같이 중괄호에 함수명만 쓴다.

- State: 리액트의 내장 객체로서 컴포넌트의 데이터 또는 정보를 저장하는데 사용
  
- useState 사용법: import { useState } from 'react'를 한 다음 const[count, setCount] useState(0)를 통해 배열로 값을 매핑한다. count에는 ()안에 지정한 초기값이, setCount에는 업데이트 함수가 들어간다.
  
- react에서 인자값 넘겨주는 법: <컴포넌트명 인자이름=인자값>을 통해 건네주고 컴포넌트 내에서는 function 컴포넌트명({인자이름}) 으로 사용한다. 함수도 인자로 넘겨 줄 수 있다. 

- import { PropTypes } from 'prop-types' 를 한 다음

  컴포넌트명.propTypes ={
    인자: PropTypes.number
  } //인자값의 타입을 지정해 줄 수 있다. 다만 실행은 가능/ 경고만 발생
  컴포넌트명.defaultProps ={
    인자명: 1
  } //인자값의 기본값을 지정해 줄 수 있다.



11. Spring Boot와 React로 Java풀스택 Todo 애플리케이션 만들기
- 리액트에서 양식 요소를 다룰 때 유의해야 할 두 가지 사항 - 리액트에서는 이 두가지가 동기화 되어야 한다. 그러므로 value값이 이미 들어가 있는 것을 수정하려면 제어 컴포넌트가 필요하다. 
  1. React State - React State에서 양식에 사용되는 변수 값을 유지할 수 있다.
  2. 양식 요소 자체의 값 - DOM 값
- 제어 컴포넌트로 만드는 법
  1. 먼저 value로 들어갈 값을 useState를 사용하여 값을 준다.
  2. onChange롤 실행 할 함수를 만든다.
  3. 추가한 함수에 event로 매개변수를 받는다.
  4. event.target.value에는 수정한 값이 들어가 있다.
  5. useState의 업데이트 함수에 event.target.value를 인자로 넣는다.  

- {showSuccessMessage && <div className='successMessage'>Authenticated Successfully</div>} 이와 같이 boolean 값과 and 연산자와 참일 때 출력하고자 하는 값을 입력하면 간단하게 구현 가능하다.

- 리액트에서 라우팅 방법 
  1. npm install react-router-dom: NPM install로 React Router DOM 패키지를 설치하여 의존성을 추가한다.
  2. 라우팅을 하려는 컴포넌트에 import { BrowserRouter, Routes ,Route, useNavigate } from 'react-router-dom' 하여 임포트 한다.
  3. 메인 컴포넌트에 다음과 같이 구조를 만든다.
     <div className="TodoApp">
          <BrowserRouter>
              <Routes>
                  <Route path='/' element={<LoginComponent />} />
                  <Route path='/login' element={<LoginComponent />} />
                  <Route path='/welcome' element={<WelcomeComponent />} />
              </Routes>
          </BrowserRouter>
      </div>
      BrowserRouter 태그안에 Routes 태그를 넣고 Routes태그 안에 Route태그와 path속성을 이용해 경로를 설정하고 element속성을 이용해 해당 경로에 출력하고자 하는 컴포넌트를 설정한다.
  4. 리다이렉트를 할 컴포넌트에 const navigate = useNavigate();를 하여 navigate 함수를 받고
  5. 분기문에 navigate('/welcome'); 를 하여 해당 경로로 리다이렉트가 가능하다. 

- 잘못된 라우터 경로 입력시 에러 컴포넌트 출력하는 법
  1. 먼저 출력할 에러 컴포넌트를 만든다.
  2. <Route path='*' element={<ErrorComponent />} /> 이와 같이 path속성에 * 를 입력해 잘못된 모든 주소를 ErrorComponent로 출력한다.
 
- 다른 컴포넌트로 파라미터 전달하는 방법
  1. {useParams } from 'react-router-dom' 를 추가로 임포트 한다.
  2. <Route path='/welcome/:username' element={<WelcomeComponent />} /> 다음과 같이 path에 :를 사용하여 키값을 넣는다.
  3. navigate(`/welcome/${username}`) 부분에서 백틱과 ${}를 사용하여 밸류값을 넣는다.
  4. 받아오는 컴포넌트 부분에서  const params = useParams() 또는 const {username} = useParams() 을 사용하여 값을 받아온다. params을 사용할 경우 params.username으로 밸류값을 가져올 수 있다.

- 리액트에서 페이지 새로고침 없이 이동하는 방법
  1. {Link } from 'react-router-dom' 를 추가로 임포트 한다.
  2. <a>태그 대신 <link>태그를 이용하여 <Link to='/todos'>Go here</Link> to 속성에 주소값을 입력한다.
  - <BrowserRouter>태그 안에 있는 컴포넌트만 사용 가능하다. 

- 컨텍스트를 생성해 리액트에서 State 공유하기
  1. Context.js 파일을 생성한다.
  2. import { createContext } from "react"를 임포트 한다.
  3. export const AuthContext = createContext();  createContext(); 함수를 받을 변수를 생성한뒤 export하여 내보낸다.
  4. 공유하기 위한 함수 컴포넌트를 생성한다. - 매개변수에 {children} 을 쓰면 모든 자식은 이 변수에 할당 된다.
  
     export const AuthContext = createContext();
     
     export default function AuthProvider({children}) {
      const [number, setNumber] = useState(0);
      return (
          <AuthContext.Provider value={ {number} }>  //value 값으로 공유할 값을 넣는다. 
              {children}
          </AuthContext.Provider>
      );
    } 
     
  5. 생성한 컴포넌트를 총괄 컴포넌트에서 부모 컴포넌트로 둔다.
  6. 값을 받는 컴포넌트에서는 useContext와 AuthContext를 임포트 하여 사용한다.
    
     const authContext = useContext(AuthContext); 
     console.log(authContext.number) //number는 value에서 전달해준 이름 그대로 사용한다.
 
  또는
  
    AuthContext.js에서 export const useAuth = () => useContext(AuthContext); //useContext를 더 쉽게 접근하게 하는 함수를 만들고
    값을 받는 컴포넌트에서
      const authContext = useAuth();  //Context를 더 쉽게 꺼내 쓸수 있게 한다.

  밑에 방식을 많이 채택하여 사용함.

- 인증 라우터로 React라우터 보호하기 -로그인이 된 상태에만 라우터로 이동이 가능하게 하기
  1. 로그인과 로그아웃 관련 로직을 AuthContext.js에 넣는다.
  2. 로그인과 로그아웃 함수도 Provider value에 넣어 함수에 실어 보낸다.
  3. 총괄 컴포넌트에서 인증라우트 컴포넌트를 생성한뒤 매개변수로 {children}을 받아와 authContex에서 isAuthenticated이 참일 때만 children을 반환한다.
  4. 참이 아닐때는 <Navigate>태그를 이용해 <Navigate to="/" />를 반환한다.
  5. 인증을 받을때만 들어갈수 있는 컴포넌트를 인증라우트 컴포넌트의 자식 컴포넌트로 넣는다.
     <Route path='/welcome/:username' element={
        <AuthenticatedRoute>
            <WelcomeComponent />
        </AuthenticatedRoute>
      } />
