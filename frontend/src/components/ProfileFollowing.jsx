import { Link } from 'react-router-dom'
import defaultUserImage from '../assets/default_profile_image.jpeg'
import './Profile.css'

const ProfileFollowing = ({ following }) => {
  return (
    <div className={following.length > 0 ? "following-container" : "no-following-container"}>
      {following.length > 0 ? (
        following.map((user) => (
          <div key={user.id} className="following-item">
            <img src={user.profileImageUrl || defaultUserImage} alt={user.username} className="following-image" />
            <Link to={`/profile/${user.username}`}>{user.username}</Link>
          </div>
        ))
      ) : (
        <p>Find some friends and follow their profile, or try to follow people that memes you find funny</p>
      )}
    </div>
  )
}

export default ProfileFollowing
