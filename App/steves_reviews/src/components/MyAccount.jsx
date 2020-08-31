import React, {useContext, useEffect, useState} from 'react';
import Review from "./Review";
import {UserContext} from "./UserContext";
import UserStats from "./UserStats";

export default function MyAccount() {

    const [userStats, setUserStats] = useState({});
    const [user, setUser] = useState({});
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    const context = useContext(UserContext);

    useEffect(() => {
        async function fetchData() {
            const userStatsResult = await fetch('/api/user/stats', {
                headers: {
                    'Authorization': 'Bearer ' + context.cookie
                }
            });
            const userStats = await userStatsResult.json();
            setUserStats(userStats);

            const reviewResult = await fetch('/api/review/user', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + context.cookie
                }
            });
            const reviews = await reviewResult.json();
            setReviews(reviews);

            const userResult = await fetch('/api/user', {
                headers: {
                    'Authorization': 'Bearer ' + context.cookie
                }
            });
            const user = await userResult.json();
            setUser(user);

            setLoading(false);
        }

        fetchData();
    }, [context]);

    if (loading)
        return (
            <>
                <br/>
                <i className='fas fa-4x fa-stroopwafel fa-spin'></i>
                <br/>
                <br/>
            </>)

    return(
        <div className='columns'>
            <div className='column is-one-third'>
                <article className='message'>
                    <div className='message-header'>
                        Edit your account
                    </div>
                    <div className='message-body'>
                        <form>
                            <input id='editUserName' type='text'/>
                            <label htmlFor='editUserName'>Username</label>

                            <input id='editUserEmail' type='email'/>
                            <label htmlFor='editUserEmail'>Email</label>

                        </form>
                    </div>
                </article>

                <UserStats user={user} userStats={userStats} />
            </div>

            <div className='column is-two-thirds'>
                <div className="level">
                    <div className="level-item is-center">
                        <h1 className="title has-text-centered">My Reviews</h1>
                    </div>
                </div>
                {
                    reviews.length === 0 ?
                        'No reviews yet'
                        :
                        reviews.map(rwa => {
                            return <Review key={rwa.review.id} reviewDto={rwa}/>
                        })
                }
            </div>
        </div>

    );


}