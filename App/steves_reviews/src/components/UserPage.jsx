import React, {useState, useEffect, useContext} from 'react';
import UserStats from "./UserStats";
import Review from "./Review";

export default function UserPage(props) {

    let id = props.match.params.id;

    const [userStats, setUserStats] = useState({});
    const [user, setUser] = useState({});
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchUserStats();
        fetchUser();
        fetchReviews();
        setLoading(false);
    }, []);

    async function fetchUserStats() {
        const userStatsResult = await fetch('/api/user/stats/' + id);
        const userStats = await userStatsResult.json();
        setUserStats(userStats);
        console.log(userStats);
    }

    async function fetchUser() {
        const userResult = await fetch('/api/user/' + id);
        const user = await userResult.json();
        setUser(user);
        console.log(user);
    }

    async function fetchReviews() {
        const reviewResult = await fetch('/api/review/user/' + id);
        const reviews = await reviewResult.json();
        console.log(reviews);
        setReviews(reviews);
    }

    if (loading)
        return (
            <div>
                loading
            </div>
        )

    return (
        <div className='columns'>
            <div className='column is is-one-third'>
                <UserStats user={user} userStats={userStats}/>
            </div>
            <div className='column is-two-thirds'>
                <div className='level'>
                    <div className='level-item is-center'>
                        <h1 className='title is-center'>User Reviews</h1>
                    </div>
                </div>
                {
                    reviews.map(rwa => {
                        return <Review key={rwa.review.id} artist={rwa.artist} album={rwa.album} review={rwa.review}/>
                    })
                }
            </div>
        </div>
    )
}