import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import './user.css';
import { FaUserCircle, FaEnvelope } from 'react-icons/fa';
import { FaApple, FaHeart, FaStar, FaCoffee, FaLeaf, FaSnowflake, FaSmile, FaRocket, FaTree, FaCar } from 'react-icons/fa';

function UserPage() {
    const [user, setUser] = useState(null);
    const [randomIcons, setRandomIcons] = useState([]);
    const { id } = useParams(); // Получаем ID пользователя из URL

    useEffect(() => {
        // Получаем данные пользователя по ID
        const fetchUserData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/users/${id}`);
                if (!response.ok) {
                    throw new Error('Ошибка при загрузке данных пользователя');
                }
                const userData = await response.json();
                setUser(userData);
            } catch (error) {
                console.error('Ошибка:', error);
            }
        };

        fetchUserData();

        // Генерация случайных иконок
        const icons = generateRandomIcons(200);
        setRandomIcons(icons);
    }, [id]); // Зависимость от id

    function generateRandomIcons(count) {
        const positions = [];
        const icons = [];
        const iconTypes = [FaApple, FaHeart, FaStar, FaCoffee, FaLeaf, FaSnowflake, FaSmile, FaRocket, FaTree, FaCar, FaEnvelope];
        const radius = 50;

        function isOverlapping(newTop, newLeft) {
            return positions.some(([top, left]) => {
                const distance = Math.sqrt(Math.pow(top - newTop, 2) + Math.pow(left - newLeft, 2));
                return distance < radius;
            });
        }

        while (icons.length < count) {
            const top = Math.random() * (window.innerHeight - radius);
            const left = Math.random() * (window.innerWidth - radius);

            if (!isOverlapping(top, left)) {
                positions.push([top, left]);
                const Icon = iconTypes[Math.floor(Math.random() * iconTypes.length)];
                icons.push({ Icon, position: { top: `${top}px`, left: `${left}px` } });
            }
        }
        return icons;
    }

    return (
        <div className="userPage-userContainer">
            <header className="userHeader">Страница пользователя</header>

            <Link to={`/start/${id}`}>
                <button className="FromUserToStartBack-button">Назад</button>
            </Link>

            <main>
                {user ? (
                    <div className="userInfoContainer">
                        <img 
                            src={user.img} 
                            alt={`${user.username}'s avatar`} 
                            className="userAvatar" 
                            style={{ width: '250px', height: '300px' }} // Установка размера изображения
                        />
                        <h1><FaUserCircle /> {user.username}</h1>
                        <p><FaEnvelope /> Email: {user.email}</p>
                        <Link to="/" className="loginButton">К авторизации</Link>
                    </div>
                ) : (
                    <p>Загрузка...</p>
                )}

                {randomIcons.map((icon, index) => {
                    const IconComponent = icon.Icon;
                    return (
                        <IconComponent 
                            key={`icon-${index}`} 
                            className="randomIcon" 
                            style={{ top: icon.position.top, left: icon.position.left }} 
                        />
                    );
                })}
            </main>
            <footer className="userPage-userFooter">
                &copy; 2024 Financial Control Application
            </footer>
        </div>
    );
}

export default UserPage;