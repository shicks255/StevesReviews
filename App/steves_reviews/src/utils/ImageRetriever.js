import NoImage from "../images/no-album-cover.png";

function getCoverArt(json) {
    if (json.images)
        return json.images[0].image.replace("http", "https");
}

function safeGet(url) {
    return url.replace('http', 'https')
}

async function getCoverArtThumb(album) {
    if (album.images.length > 0) {
        const images = album.images;
        if (images.find(x => x.size === 'small'))
            return images.find(x => x.size === 'small').url;
        else if(images.find(x => x.size === '250'))
            return images.find(x => x.size === '250').url;
        if (images.find(x => x.size === '500'))
            return images.find(x => x.size === '250').url;
        else if (images.find(x => x.size === 'large'))
            return images.find(x => x.size === 'large').url;
        else if (images.find(x => x.size === 'main'))
            return images.find(x => x.size === 'main').url;
        else
            return images[0].url;
    }
    else {
        const data = await fetch('https://coverartarchive.org/release-group/' + album.id);
        if (data.status !== 200)
            return NoImage;
        else {
            const json = await data.json();
            const image = getCoverArt(json);
            return image;
        }
    }
}

async function getCarouselArt(images) {
    if (images.length === 0){
        const p = {
            url: NoImage
        }
        return [p];
    }

    let front = images.find(x => x.text === 'front' && x.size === 'main');
    if (!front)
        front = images.find(x => x.text === 'front' && x.size === 'large');
    if (!front)
        front = images.find(x => x.text === 'front' && x.size === '1200');

    let back = images.find(x => x.text === 'back' && x.size === 'main');
    if (!back)
        back = images.find(x => x.text === 'back' && x.size === 'large');
    if (!back)
        back = images.find(x => x.text === 'back' && x.size === '1200');

    let booklets = images.filter(x => x.text === 'booklet' && x.size === 'main');
    let mediums = images.filter(x => x.text === 'medium' && x.size === 'main');
    let trays = images.filter(x => x.text === 'tray' && x.size === 'main');
    let others = images.filter(x => x.text === 'other' && x.size === 'main');

    let x = [front, back].concat(booklets, mediums, trays, others);
    return x;
}

export { getCoverArt, getCoverArtThumb, getCarouselArt, safeGet };