import {useParams, Link } from 'react-router-dom';
import { useState } from 'react';
import { retrieveHelloWorldPathVariable } from './api/HelloWorldApiService';
import { useAuth } from './security/AuthContext';

export default function WelcomeComponent() {

    //const params = useParams();
    //console.log(params.username);

    const {username} = useParams();
    console.log(username);

    const [message, setMessage] = useState(null);

    const authContext = useAuth();


    function callHelloWorldRestApi() {

        retrieveHelloWorldPathVariable('Ranga', authContext.token)
            .then((response) => successfulResponse(response)) 
            .catch((error) => errorResponse(error)) 
            .finally(() => console.log('cleanup')); 

    }

    function successfulResponse(response) {
        console.log(response);
        setMessage(response.data.message);
    }

    function errorResponse(error) {
        console.log(error);
    }

    return(
        <div className="WelcomeComponent">
            <h1>Welcome {username}</h1>
            <div>
                Manage your todeos - <Link to='/todos'>Go here</Link>
            </div>
            <div>
                <button className='btn btn-success m-5' onClick={callHelloWorldRestApi}>
                    Call Hello World REST API
                </button>
            </div>
            <div className='text-info'>{message}</div>
        </div>
    );
}