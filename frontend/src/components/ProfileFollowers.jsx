import { Link } from 'react-router-dom'
import defaultUserImage from '../assets/default_profile_image.jpeg'
import './Profile.css'

const ProfileFollowers = ({ followers }) => {
  return (
    <div className={followers.length > 0 ? "followers-container" : "no-followers-container"}>
      {followers.length > 0 ? (
        followers.map((user) => (
          <div key={user.id} className="following-item">
            <img src={user.profileImageUrl || defaultUserImage} alt={user.username} className="following-image" />
            <Link to={`/profile/${user.username}`}>{user.username}</Link>
          </div>
        ))
      ) : (
        <p>Looks like none is here, you can be the first one</p>
      )}
    </div>
  )
}

export default ProfileFollowers
