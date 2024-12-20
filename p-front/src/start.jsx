 // start.jsx
 import React, { useState, useEffect } from 'react';
 import { Link, useParams } from 'react-router-dom';
 import './start.css';
 
 function StartPage() {
     const [currentTime, setCurrentTime] = useState(new Date());
     const { id } = useParams();

     useEffect(() => {
         const intervalId = setInterval(() => {
             setCurrentTime(new Date());
         }, 1000);
 
         return () => clearInterval(intervalId);
     }, []);
 
     const formattedTime = currentTime.toLocaleString();
 
     return (
         <div className="start-container">
             <div className="start-date-time">
                 <p>{formattedTime}</p>
             </div>
             <h1 className="start-container h1">Добро пожаловать</h1>
             <div className="start-button-group">
                 <Link to={`/user/${id}`}>
                     <button data-tooltip="Перейти к странице пользователя">Страница пользователя</button>
                 </Link>
                 <Link to={`/category/${id}`}>
                     <button data-tooltip="Просмотр категорий накоплений">Категории накоплений</button>
                 </Link>
                 <Link to={`/transactions/${id}`}> 
                     <button data-tooltip="Список всех транзакций">Список транзакций</button>
                 </Link>
                 <Link to={`/finance/${id}`}>
                     <button data-tooltip="Просмотр и управление финансами">Финансы</button>
                 </Link>
             </div>
             <div className="rgb-shimmer"></div>
         </div>
     );
 }
 
 export default StartPage;
 