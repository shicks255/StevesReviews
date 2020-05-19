import React, {useContext, useEffect, useState} from 'react';
import Review from "./Review";
import {UserContext} from "./UserContext";
import UserStats from "./UserStats";

export default function MyAccount(props) {

    const [userStats, setUserStats] = useState({});
    const [user, setUser] = useState({});
    const [reviews, setReviews] = useState([]);

    const context = useContext(UserContext);

    useEffect(() => {
        fetchUserStats();
        fetchUser();
        fetchReviews();
    }, []);

    async function fetchUserStats() {
        const userStatsResult = await fetch('/api/user/stats', {
            headers: {
                'Authorization': 'Bearer ' + context.cookie
            }
        });
        const userStats = await userStatsResult.json();
        setUserStats(userStats);
    }

    async function fetchUser() {
        const userResult = await fetch('/api/user', {
            headers: {
                'Authorization': 'Bearer ' + context.cookie
            }
        });
        const user = await userResult.json();
        setUser(user);
    }

    async function fetchReviews() {
        const reviewResult = await fetch('/api/review/user', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + context.cookie
            }
        });
        const reviews = await reviewResult.json();
        console.log(reviews);
        setReviews(reviews);
    }

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
                    </div>
                    <h1 className="title is-center">My Reviews</h1>
                </div>
                {
                    reviews.map(rwa => {
                        return <Review key={rwa.review.id} artist={rwa.artist} album={rwa.album} review={rwa.review}/>
                    })
                }
            </div>
        </div>

    );


}