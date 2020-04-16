import React, {useEffect, useState} from 'react';
import Review from "./Review";
import SiteStats from "./SiteStats";

function Main() {

    const [recentReviews, setRecentReviews] = useState([]);

    async function fetchRecentReviews() {
        const data = await fetch('/review/recent');
        const reviews = await data.json();
        console.log(reviews);
        setRecentReviews(reviews);
    }

    useEffect(() => {
        fetchRecentReviews()
    }, []);

    return (
        <div>
            <div className="level">
                <div className="level-item is-centered centerText">
                    These are reviews of some albums I have listened to over the years.
                    <br/>
                    Please feel free to add your own reviews
                </div>
            </div>

            <div className="container">
                <div className="columns">
                    <div className="column is-one-quarter">
                        <SiteStats />
                    </div>

                    <div className="column is-three-quarters">
                        <div className="level">
                            <div className="level-item is-center">
                                <h1 className="title">Recent Reviews</h1>
                            </div>
                        </div>
                        {recentReviews.map(rwa => {
                            return <Review key={rwa.review.id} album={rwa.album} review={rwa.review}/>
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Main;
