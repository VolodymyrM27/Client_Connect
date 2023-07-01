import React from 'react';
import {Typography, Divider, LinearProgress, Box} from '@mui/material';
import {PieChart, Pie, Cell, Legend, Tooltip} from 'recharts';

export function VisitorStatistics({data}) {
    const genderData = [
        {name: 'Male', value: data.male_users},
        {name: 'Female', value: data.female_users}
    ];

    const COLORS = ['#0088FE', '#FF8042'];

    return (
        <div>
            <Typography variant="h6">Average Age:</Typography>
            <Box display="flex" alignItems="center" p={1} m={1}>
                <Box width="100%" mr={1}>
                    <LinearProgress variant="determinate" value={data.average_age} style={{ backgroundColor: '#dedede', height: '10px', borderRadius: '5px' }} color="secondary">
                        <div style={{ width: `${data.average_age}`, height: '100%', borderRadius: '5px' }}></div>
                    </LinearProgress>
                </Box>
                <Box minWidth={35}>
                    <Typography variant="body2" color="textSecondary">{`${Math.round(
                        data.average_age,
                    )}`}</Typography>
                </Box>
            </Box>
            <Divider variant="middle"/>
            <Typography variant="h6">Gender Distribution:</Typography>
            <PieChart width={400} height={400}>
                <Pie
                    data={genderData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={100}
                    fill="#8884d8"
                    label
                >
                    {
                        genderData.map((entry, index) => <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]}/>)
                    }
                </Pie>
                <Tooltip/>
                <Legend/>
            </PieChart>
        </div>
    );
}
