import { useEffect, useState } from 'react'
import { useParams, Link, useLocation } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode';
import defaultUserImage from '../assets/default_profile_image.jpeg'
import './Profile.css'
import ProfileFollowers from './ProfileFollowers'
import ProfileFollowing from './ProfileFollowing'
import ProfileMemes from './ProfileMemes'

const Profile = ({ token }) => {
  const location = useLocation()
  const [profile, setProfile] = useState(null)
  const [memes, setMemes] = useState([])
  const [following, setFollowing] = useState([])
  const [followers, setFollowers] = useState([])
  const [isFollowing, setIsFollowing] = useState(false)
  const [error, setError] = useState(null)
  const [activeTab, setActiveTab] = useState('memes')
  const { username } = useParams()
  const decodedToken = jwtDecode(token)
  const isOwner = decodedToken.sub === username
  const queryParams = new URLSearchParams(location.search)
  const refreshTimestamp = queryParams.get('refresh')

  const fetchProfileData = async () => {
    try {
      const profileResponse = await fetch(`http://localhost:8080/api/user/username/${username}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (profileResponse.ok) {
        const profileData = await profileResponse.json()
        setProfile(profileData)
      } else {
        throw new Error('Failed to fetch profile')
      }

      const memesResponse = await fetch(`http://localhost:8080/api/memes/username/${username}/0/1000`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (memesResponse.ok) {
        const memesData = await memesResponse.json()
        setMemes(memesData)
      } else {
        throw new Error('Failed to fetch memes')
      }

      const followingResponse = await fetch(`http://localhost:8080/api/following/username/${username}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (followingResponse.ok) {
        const followingData = await followingResponse.json()
        setFollowing(followingData)
      } else {
        throw new Error('Failed to fetch following')
      }

      const followersResponse = await fetch(`http://localhost:8080/api/followers/username/${username}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (followersResponse.ok) {
        const followersData = await followersResponse.json()
        setFollowers(followersData)
        setIsFollowing(followersData.some(follower => follower.username === decodedToken.sub))
      } else {
        throw new Error('Failed to fetch followers')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  const handleFollow = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/follow', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          follower: { id: decodedToken.userId },
          followed: { id: profile.id },
        }),
      })
      if (response.ok) {
        setIsFollowing(true)
        fetchProfileData()
      } else {
        throw new Error('Failed to follow user')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  const handleUnfollow = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/unfollow', {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          followerId: decodedToken.userId,
          followedId: profile.id,
        }),
      })
      if (response.ok) {
        setIsFollowing(false)
        fetchProfileData()
      } else {
        throw new Error('Failed to unfollow user')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    if (token && username) {
      fetchProfileData()
    }
  }, [token, username, refreshTimestamp])

  if (error) return <p>Error: {error}</p>
  if (!profile) return <p>Loading...</p>

  return (
    <div>
      <div className="profile-container">
        <div className='profile-image-container'>
          <img 
            className="profile-image" 
            src={profile.profileImageUrl || defaultUserImage} 
            alt="Profile" 
          />
        </div>
        <div className="profile-details">
            <div className='profile-info'>
                <h2>
                    {profile.username} 
                </h2>
                <p>{profile.email}</p>
                <p>{profile.bio || 'No bio yet.'}</p>
            </div>
            <div className='profile-buttons-container'>
            {isOwner && (
              <div className="profile-buttons">
                <Link to="/profile/edit" className="edit-profile-button">
                  Edit
                </Link>
                <Link to="/upload-meme" className="upload-meme-button">
                  Upload Meme
                </Link>
                <Link to="/manage" className="manage-profile-button">
                  Manage
                </Link>
              </div>
            )}
            {!isOwner && (
              isFollowing ? (
                <button onClick={handleUnfollow} className="follow-button">Unfollow</button>
              ) : (
                <button onClick={handleFollow} className="follow-button">Follow</button>
              )
            )}
            </div>
        </div>
      </div>
      
      <div className="profile-stats">
        <span
          onClick={() => {
            if (activeTab !== 'memes') {
              setActiveTab('memes')
              fetchProfileData()
            }
          }}
          className={activeTab === 'memes' ? 'active-tab' : ''}
        >
          Memes
        </span>
        <span onClick={() => { setActiveTab('followers'); fetchProfileData(); }}>
          Followers
        </span>
        <span onClick={() => { setActiveTab('following'); fetchProfileData(); }}>
          Following
        </span>
      </div>
      
      {activeTab === 'following' && <ProfileFollowing following={following} />}
      {activeTab === 'followers' && <ProfileFollowers followers={followers} />}
      {activeTab === 'memes' && <ProfileMemes memes={memes} isOwner={isOwner} />}
    </div>
  )
}

export default Profile