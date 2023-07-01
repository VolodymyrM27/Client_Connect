import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Typography } from '@mui/material';
import { styled } from '@mui/system';

const StyledDiv = styled('div')(({ theme }) => ({
    marginBottom: theme.spacing(4),
}));

const TitleTypography = styled(Typography)(({ theme }) => ({
    marginBottom: theme.spacing(2),
}));


export function UserVisitsByDaysChart({data}) {
    return (
        <StyledDiv>
            <TitleTypography variant="h6">
                Top visits by day

            </TitleTypography>
            <ResponsiveContainer width="100%" height={400}>
                <LineChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="day" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="visits" stroke="#8884d8" fill="#8884d8" />
                </LineChart>
            </ResponsiveContainer>
        </StyledDiv>
    );
}

