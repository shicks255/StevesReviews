import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import NoImage from '../images/no-album-cover.png';


export default function Review(props) {
    const {review, album, artist, rating} = props;
    const [collapsed, setCollapsed] = useState(true);
    const [image, setImage] = useState(NoImage);

    async function getImage(id) {
        const result = await fetch('http://coverartarchive.org/release-group/' + id);
        const imageData = await result.json();
        setImage(imageData.images[0].image);
    }

    useEffect(() => {
        getImage(album.id);
    }, []);

    let imageStuff = '';
    let contentColumnClass = 'is-full';
    if (!props.hideAlbum) {
        contentColumnClass = 'is-three-quarters';
        imageStuff =
            <div className="column is-one-quarter">
            <Link to={`/artist/${artist.id}`}><b>{artist.name}</b></Link>
            <figure className="image is-128x128">
                <Link to={`/album/${album.id}`}>
                    <img alt='myimage' src={image}/>
                </Link>
            </figure>
            <Link to={`/album/${album.id}`}>{album.title}</Link>
        </div>
    }

    return(
        <div className="box">
            <div className="columns noPad">
                {imageStuff}
                <div className={`column ${contentColumnClass}`}>
                    <div className=''>
                        <Link to={`user/${review.user.id}`}>{review.user.username}</Link>
                        <br/>
                        <span className={`tag ${review.colorClass}`}>
                            Rating: {rating ? rating : review.rating}
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