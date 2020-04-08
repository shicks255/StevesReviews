import React, {useEffect, useState} from 'react';

export default function TopRated() {

    const [albumsAndRatings, setAlbumsAndRatings] = useState([]);

    async function loadAlbumsAndRatings() {
        const data = await fetch('/album/topRated')
        const albumsAndRatings = await data.json();
        console.log(albumsAndRatings);
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
                return (
                    <tr>
                        <td>{index + 1}</td>
                        <td>
                            <a href="@controllers.routes.ArtistController.artistHome(a.artist.id.get)"><b>{ar.album.artist.name}</b></a>
                            <figure className="image is-128x128">
                                <a href="@controllers.routes.AlbumController.albumHome(a.id)" >
                                    <img src={ar.imageUrl}/>
                                </a>
                            </figure>
                            <a href="@controllers.routes.AlbumController.albumHome(a.id)">{ar.album.name}</a>
                        </td>
                        <td>{ar.averageRating}</td>
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}