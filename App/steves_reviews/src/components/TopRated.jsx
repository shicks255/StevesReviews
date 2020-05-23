import React, {useEffect, useState} from 'react';
import Review from "./Review";

export default function TopRated() {

    const [albumsAndRatings, setAlbumsAndRatings] = useState([]);
    const [loading, setLoading] = useState(true);

    async function loadAlbumsAndRatings() {
        const data = await fetch('/api/album/topRated')
        const albumsAndRatings = await data.json();
        setAlbumsAndRatings(albumsAndRatings);
        setLoading(false);
    }

    useEffect(() => {
        loadAlbumsAndRatings();
    }, []);

    if (loading) {
        return (
            <>
                <br/>
                <i className='fas fa-4x fa-stroopwafel fa-spin'></i>
                <br/>
                <br/>
            </>
        )
    }

    return (
        <div className='columns'>
            <div className='column is-12-mobile is-10-desktop is-offset-1-desktop'>
                <table className="table">
                    <tbody>
                    {albumsAndRatings.map((ar, index) => {
                        return <Review rank={index} reviewDto={ar} />
                    })};
                    </tbody>
                </table>
            </div>
        </div>
    )
}
