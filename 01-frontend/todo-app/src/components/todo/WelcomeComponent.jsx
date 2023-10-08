import {useParams, Link } from 'react-router-dom';

export default function WelcomeComponent() {

    //const params = useParams();
    //console.log(params.username);

    const {username} = useParams();
    console.log(username);

    return(
        <div className="WelcomeComponent">
            <h1>Welcome {username}</h1>
            <div>
                Manage your todeos - <Link to='/todos'>Go here</Link>
            </div>
        </div>
    );
}