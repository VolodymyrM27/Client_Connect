import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Typography } from '@mui/material';
import { styled } from '@mui/system';

const StyledDiv = styled('div')(({ theme }) => ({
    marginBottom: theme.spacing(4),
}));

const TitleTypography = styled(Typography)(({ theme }) => ({
    marginBottom: theme.spacing(2),
}));

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

export function UserVisitsByMonthsChart({data}) {
    const processedData = data.map(d => ({...d, month: months[d.month-1]}));
    const fullData = months.map((month, i) => {
        const existingData = processedData.find(d => d.month === month);
        return existingData ? existingData : {month: month, visits: 0};
    });

    return (
        <StyledDiv>
            <TitleTypography variant="h6">
                Top months by visits
            </TitleTypography>
            <ResponsiveContainer width="100%" height={400}>
                <LineChart data={fullData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="month" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="visits" stroke="#8884d8" fill="#8884d8" />
                </LineChart>
            </ResponsiveContainer>
        </StyledDiv>
    );
}
