import React, {useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import Header from './components/Header';
import Footer from "./components/Footer";

function App() {

    const [recentReviews, setRecentReviews] = useState([]);

    async function fetchRecentReviews() {
        const data = await fetch('/review/recent');
        const reviews = data.json();
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

            These are reviews of some albums I have listened to over the years.
            Please feel free to add your own reviews

            <div className="container">
                <div className="siteStats">

                </div>
                <div className="recentReviews">
                    {recentReviews.map(rev => {
                        return (
                            <p>
                                {rev.album.name}
                            </p>
                        )
                    })}
                </div>
            </div>

            <Footer/>
        </div>
    );
}

export default App;
