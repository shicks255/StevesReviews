import React, {useContext, useEffect, useState} from 'react';
import Review from "./Review";
import {UserContext} from "./UserContext";

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
        const userStatsResult = await fetch('/user/stats', {
            headers: {
                'Authorization': 'Bearer ' + context.cookie
            }
        });
        const userStats = await userStatsResult.json();
        setUserStats(userStats);
    }

    async function fetchUser() {
        const userResult = await fetch('/user', {
            headers: {
                'Authorization': 'Bearer ' + context.cookie
            }
        });
        const user = await userResult.json();
        setUser(user);
    }

    async function fetchReviews() {
        const reviewResult = await fetch('/review/user', {
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

                <article className="message">
                    <div className="message-header">
                        {user.username}
                    </div>
                    <div className="message-body">
                        <table className="table">
                            <tbody>
                            <tr>
                                <td>Reviews:</td>
                                <td>{userStats.reviews}</td>
                            </tr>
                            <tr>
                                <td>5-Star Reviews:</td>
                                <td>{userStats.fiveStarReviews}</td>
                            </tr>
                            <tr>
                                <td>Average Rating:</td>
                                <td>{userStats.averageRating}</td>
                            </tr>
                            <tr>
                                <td>Average Review Length:</td>
                                <td>{userStats.averageReviewLength} words</td>
                            </tr>
                            <tr>
                                <td>Last Review:</td>
                                <td>{userStats.lastReview}</td>
                            </tr>
                            </tbody>

                        </table>
                    </div>
                </article>
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