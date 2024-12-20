import React, { useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Title } from 'chart.js';
import './finance.css';
import { Link, useParams } from 'react-router-dom';

ChartJS.register(ArcElement, Tooltip, Legend, Title);

function FinancePage() {
    const [labelsState, setLabelsState] = useState([]); // Initialize as empty array
    const [dataState, setDataState] = useState([]); // Initialize as empty array
    const {id} = useParams();

    const addData = (e) => {
        e.preventDefault();
        const target = e.target;
        const goal = target.querySelector('.add-goal').value;
        const amount = parseInt(target.querySelector('.add-amount').value, 10); // Parse int here

        if (!isNaN(amount) && goal.trim() !== '') { //Added validation
            setLabelsState(prevState => [...prevState, goal]);
            setDataState(prevState => [...prevState, amount]);
        } else {
            alert('Please enter valid goal and amount'); //Alert for invalid input
        }

        target.reset();
    };

    const generateColors = (numColors) => {
        const colors = [
            '#FF6384',
            '#36A2EB',
            '#FFCE56',
            '#4BC0C0',
            '#9966FF',
            '#FF9933',
            '#FFCC99',
            '#99CCFF',
            '#CCFFCC',
            '#FFFF99'
        ];
        return Array.from({ length: numColors }, (_, i) => colors[i % colors.length]);
    };

    const options = {
        plugins: {
            legend: {
                labels: {
                    font: { size: 18 },
                    color: 'white'
                }
            }
        }
    };

    // Data is generated dynamically based on labelsState and dataState
    const data = {
        labels: labelsState,
        datasets: [{
            data: dataState,
            backgroundColor: generateColors(labelsState.length),
            hoverBackgroundColor: generateColors(labelsState.length)
        }]
    };


    return (
        <div className="finance-page-container">
            <h1 className="finance-page-title">Финансовая информация</h1>

            <div className="finance-page-content">
                <div className="finance-page-form-container">
                    <form onSubmit={addData}>
                        <input type="text" className="add-goal" placeholder="Цель затрат" required />
                        <input type="number" className="add-amount" placeholder="Сумма" required />
                        <button type="submit">Добавить</button>
                    </form>
                </div>
                {/* Conditionally render the chart */}
                {labelsState.length > 0 && (
                    <div className="finance-page-chart-container">
                        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                            <Pie data={data} options={options} />
                        </div>
                    </div>
                )}
            </div>

            <Link to={`/start/${id}`}>
                <button className="finance-page-back-button">Назад</button>
            </Link>

            <footer className="finance-page-footer">
                <p>&copy; 2024 Financial Control Application</p>
            </footer>
        </div>
    );
}

export default FinancePage;
