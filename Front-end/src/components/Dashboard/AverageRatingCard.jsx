import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import StarIcon from '@mui/icons-material/Star';
import Box from '@mui/material/Box';
import {useTranslation} from "react-i18next";

export function AverageRatingCard({data}) {
    const { t, i18n } = useTranslation();

    return (
        <Box sx={{ width: '400px', minHeight: '100px', bgcolor: 'background.paper', borderRadius: '8px', padding: '10px', boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .3)' }}>
            <Card>
                <CardContent>
                    <Typography color="textSecondary" gutterBottom>
                        {t("Average rating by reviews")}
                    </Typography>
                    <Typography variant="h5" component="div">
                        <StarIcon /> {data}/5
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    );
}
