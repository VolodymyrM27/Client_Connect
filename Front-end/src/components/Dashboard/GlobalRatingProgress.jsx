import LinearProgress from '@mui/material/LinearProgress';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import {useTranslation} from "react-i18next";



export function GlobalRatingProgress({data}) {
    const { t, i18n } = useTranslation();

    const normalisedRating = data; // Rating already in range from 0 to 100


    const getColor = () => {
        if (normalisedRating <= 50) {
            return `rgb(${255}, ${255*normalisedRating/50}, ${255*normalisedRating/50})`; // from red to white
        } else {
            return `rgb(${255*(2-normalisedRating/50)}, ${255}, ${255*(2-normalisedRating/50)})`; // from white to green
        }
    };

    return (
        <Box sx={{ width: '500px', minHeight: '180px', bgcolor: 'background.paper', borderRadius: '8px', padding: '10px', boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .3)' }}>
            <Typography variant="body2" color="text.secondary" style={{fontSize: '12px', lineHeight: '1.2'}}>
                {t("ratingGlobal")}
            </Typography>
            <Box sx={{ m: 1 }}>
                <Typography variant="body2" color="text.secondary">
                    {t("Global Rating")}: {normalisedRating.toFixed(2)}
                </Typography>
            </Box>
            <LinearProgress variant="determinate" value={normalisedRating} style={{ backgroundColor: '#dedede', height: '10px', borderRadius: '5px' }} color="secondary">
                <div style={{ width: `${normalisedRating}%`, height: '100%', backgroundColor: getColor(), borderRadius: '5px' }}></div>
            </LinearProgress>
        </Box>
    );
}