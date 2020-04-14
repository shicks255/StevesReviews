import React, {useEffect, useState} from 'react';


export default function Review(props) {
    const {review, album} = props;

    console.log(album);

    const [collapsed, setCollapsed] = useState(true);
    const [image, setImage] = useState({});

    async function getImageUrl() {
        // const data = await fetch(`/album/${album.id}/large`);
        // const image = await data.json();
        // setImage(image);
    }

    // useEffect(() => {
    //     getImageUrl();
    // }, []);
    //
    return(
        <div className="box">
            <div className="columns noPad">
                <div className="column is-one-quarter">
                    <a href={`/artist/${album['artist-credit'][0].artist.id}`}><b>{album['artist-credit'][0].name}</b></a>
                    <figure className="image is-128x128">
                        {/*<a href="@controllers.routes.AlbumController.albumHome(r.album.id)">*/}
                        <a href='/'>
                            <img alt='myimage' src={album.images ? album.images[0][0].thumbnails['250'] : ''} />
                        </a>
                    </figure>
                    <a href={`/album/${album.id}`}>{album.title}</a>
                </div>
                <div className="column is-three-quarters">
                    <div className=''>
                        <a href="@routes.UserController.userHome2(r.user.id)">{review.user.username}</a>
                        <br/>
                        <span className={`tag ${review.colorClass}`}>
                            Rating: {review.rating}
                        </span>
                        <br/>
                        {review.addedOn}
                    </div>
                    <br/>
                    <div>
                        <span className="icon" id="reviewButton_@r.id" style={{cursor: 'pointer'}}>
                            <i className="fas fa-arrow-down"></i>
                        </span>
                        <div className="reviewContent" id="reviewContent_@r.id">
                            {review.content}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}