import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import React from 'react';
import {useTranslation} from "react-i18next";


export function ActiveVisitingHoursChart({ receivedData }) {
    const { t, i18n } = useTranslation();
    let fullData = [];
    for (let i = 0; i < 24; i++) {
        fullData.push({ hour: i, visits: 0 });
    }



    receivedData.forEach(d => {
        fullData[d.hour].visits = d.visits;
    });

    return (
        <ResponsiveContainer width="100%" height={400}>
            <LineChart data={fullData} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="hour" label={{ value: 'Hours', position: 'insideBottomRight', offset: -5 }} />
                <YAxis label={{ value: 'Visit', angle: -90, position: 'insideLeft' }} />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="visits" stroke="#8884d8" activeDot={{ r: 8 }} />
            </LineChart>
        </ResponsiveContainer>
    );
}
