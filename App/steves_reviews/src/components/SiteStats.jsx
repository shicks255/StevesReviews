import React, {useEffect, useState} from 'react';


export default function SiteStats() {

    const [stats, setStats] = useState({});

    async function getSiteStats() {
        const data = await fetch('/api/stats/siteStats')
        const siteStats = await data.json();
        setStats(siteStats);
    }

    useEffect(() => {
        getSiteStats();
    }, []);

    return (
        <article className="message">
            <div className="message-header">
                Site Stats
            </div>
            <div className="message-body">
                <table className="table is-fullwidth">
                    <tbody>
                    <tr>
                        <td>Users:</td>
                        <td className="alignRight"> {stats.users} </td>
                    </tr>
                    <tr>
                        <td>Albums:</td>
                        <td className="alignRight">{stats.albums}</td>
                    </tr>
                    <tr>
                        <td>Reviews:</td>
                        <td className="alignRight">{stats.reviews}</td>
                    </tr>
                    <tr>
                        <td>Ratings:</td>
                        <td className="alignRight">{stats.ratings}</td>
                    </tr>
                    <tr>
                        <td>5-Star Albums:</td>
                        <td className="alignRight">{stats.fiveStars}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>
    )

}