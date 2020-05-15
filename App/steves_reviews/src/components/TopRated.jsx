import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import NoImage from '../images/no-album-cover.png';
import Review from "./Review";

export default function TopRated() {

    const [albumsAndRatings, setAlbumsAndRatings] = useState([]);

    async function loadAlbumsAndRatings() {
        const data = await fetch('/album/topRated')
        const albumsAndRatings = await data.json();
        setAlbumsAndRatings(albumsAndRatings);
    }

    useEffect(() => {
        loadAlbumsAndRatings();
    }, []);

    if (albumsAndRatings.length === 0)
        return "";

    return (
        <table className="table">
            <tbody>
            {albumsAndRatings.map((ar, index) => {
                return <Review rank={index} rating={ar.average} review={ar.reviewWithAlbum.review} album={ar.reviewWithAlbum.album} artist={ar.reviewWithAlbum.artist}/>
            })};
            </tbody>
        </table>
    )
}