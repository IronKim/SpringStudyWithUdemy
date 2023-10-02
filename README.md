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
  
- useState 사용법: import { useState } from 'react'를 한 다음 const[count, setCount] useState(0)를 통해 배열로 값을 매핑한다. count에는 ()안에 지정한 초기값이, setCount에는 함수가 들어간다.
  
- react에서 인자값 넘겨주는 법: <컴포넌트명 인자이름=인자값>을 통해 건네주고 컴포넌트 내에서는 function 컴포넌트명({인자이름}) 으로 사용한다. 함수도 인자로 넘겨 줄 수 있다. 

- import { PropTypes } from 'prop-types' 를 한 다음

  컴포넌트명.propTypes ={
    인자: PropTypes.number
  } 인자값의 타입을 지정해 줄 수 있다. 다만 실행은 가능/ 경고만 발생
  컴포넌트명.defaultProps ={
    인자명: 1
  } 인자값의 기본값을 지정해 줄 수 있다.
