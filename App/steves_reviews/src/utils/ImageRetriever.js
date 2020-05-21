import NoImage from "../images/no-album-cover.png";

function getCoverArt(json) {
    if (json.images)
        return json.images[0].image.replace("http", "https");
}

async function getCoverArtThumb(album) {
    if (album.images.length > 0) {
        const images = album.images;
        if (images.find(x => x.size == 'small'))
            return images.find(x => x.size == 'small').url;
        else if (images.find(x => x.size == 'large'))
            return images.find(x => x.size == 'large').url;
        else if (images.find(x => x.size == 'main'))
            return images.find(x => x.size == 'main').url;
    }
    else {
        const data = await fetch('https://coverartarchive.org/release-group/' + album.id);
        if (data.status != 200)
            return NoImage;
        else {
            const json = await data.json();
            const image = getCoverArt(json);
            return image;
        }
    }
}

export { getCoverArt, getCoverArtThumb };