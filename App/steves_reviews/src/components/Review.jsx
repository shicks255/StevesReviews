import React, {useEffect, useState} from 'react';
import NoImage from '../images/no-album-cover.png';


export default function Review(props) {
    const {review, album, artist} = props;
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
            <a href={`/artist/${artist.id}`}><b>{artist.name}</b></a>
            <figure className="image is-128x128">
                {/*<a href="@controllers.routes.AlbumController.albumHome(r.album.id)">*/}
                <a href='/'>
                    <img alt='myimage' src={image}/>
                </a>
            </figure>
            <a href={`/album/${album.id}`}>{album.title}</a>
        </div>
    }

    return(
        <div className="box">
            <div className="columns noPad">
                {imageStuff}
                <div className={`column ${contentColumnClass}`}>
                    <div className=''>
                        <a href="@routes.UserController.userHome2(r.user.id)">{review.user.username}</a>
                        <br/>
                        <span className={`tag ${review.colorClass}`}>
                            Rating: {review.rating}
                        </span>
                        <br/>
                        {review.addedOn.monthValue + '/' + review.addedOn.dayOfMonth + '/' + review.addedOn.year}
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