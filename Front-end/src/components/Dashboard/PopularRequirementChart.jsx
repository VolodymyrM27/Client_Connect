import React, {useState, useEffect, useContext} from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, Legend } from 'recharts';
import { fetchBusinessSupportedRequirements } from "../../services/apiService";
import { Typography } from '@mui/material';
import UserContext from '../../context/user-context';



export function PopularRequirementChart({data}) {
    const [chartData, setChartData] = useState([]);
    const { businessId } = useContext(UserContext);

    useEffect(() => {
        fetchSupportedRequirements(businessId).then(supportedReq => {
            const allReq = supportedReq.map(req => ({popular_requirement_name: req.requirement_name, count: 0}));

            const updatedReq = allReq.map(req => {
                const match = data.find(d => d.popular_requirement_name === req.popular_requirement_name);
                return match ? match : req;
            });

            setChartData(updatedReq);
        });
    }, [data]);

    return (
        <>
            <Typography variant="h6" gutterBottom component="div">
                Top Popular Requirements
            </Typography>
            <BarChart width={1000} height={250} data={chartData}>
                <XAxis dataKey="popular_requirement_name"  textAnchor="end" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="count" fill="#8884d8" />
            </BarChart>
        </>
    );
}

async function fetchSupportedRequirements(businessId) {
    const response = await fetchBusinessSupportedRequirements(businessId);
    return response;
}
