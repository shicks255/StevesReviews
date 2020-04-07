import React, {useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import Header from './components/Header';
import Footer from "./components/Footer";
import Review from "./components/Review";
import SiteStats from "./components/SiteStats";

function App() {

    const [recentReviews, setRecentReviews] = useState([]);

    async function fetchRecentReviews() {
        const data = await fetch('/review/recent');
        const reviews = data.json();
        console.log(reviews);
        return reviews;
    }

    useEffect(() => {
        fetchRecentReviews().then((reviews) => {
            setRecentReviews(reviews);
        });
    }, []);

    return (
        <div className="App">
            <Header />

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
                        {recentReviews.map(rev => {
                            return (
                                <Review review={rev}/>
                            )
                        })}
                    </div>
                </div>
            </div>

            <Footer/>
        </div>
    );
}

export default App;
