import React, { useEffect, useState, useContext } from 'react';
import { fetchBusinessStatistics } from "../../services/apiService";
import { ActiveVisitingHoursChart } from '../../components/Dashboard/ActiveVisitingHoursChart'
import { AverageRatingCard } from '../../components/Dashboard/AverageRatingCard'
import { GlobalRatingProgress } from '../../components/Dashboard/GlobalRatingProgress'
import { PopularRequirementChart } from '../../components/Dashboard/PopularRequirementChart'
import { TopUsersTable } from '../../components/Dashboard/TopUsersTable'
import { UserVisitsByDaysChart } from '../../components/Dashboard/UserVisitsByDaysChart'
import { UserVisitsByMonthsChart } from '../../components/Dashboard/UserVisitsByMonthsChart'
import { VisitHistoryList } from '../../components/Dashboard/VisitHistoryList'
import { VisitorStatistics } from '../../components/Dashboard/VisitorStatistics'
import Sidebar from "../../components/Sidebar";
import { Box, Grid } from "@mui/material";
import {LatestFeedbackList} from "../../components/Dashboard/LatestFeedbackList";
import UserContext from '../../context/user-context';

function StatisticsPage() {
    const [data, setData] = useState(null);
    const { businessId } = useContext(UserContext);

    useEffect(() => {
        fetchBusinessStatistics(businessId).then(responseData => {
            setData(responseData);
        });
    }, []);

    if (!data) {
        return <div>Loading...</div>;
    }

    return (
        <Box sx={{ display: 'flex' }}>
            <Sidebar/>
            <Box component="main" sx={{ flexGrow: 1, ml: { sm: 35 }, m: 3 }}>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6}>
                        <AverageRatingCard data={data.average_business_rating_by_reviews} />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <GlobalRatingProgress data={data.global_rating} />
                    </Grid>
                    <Grid item xs={12}>
                        <ActiveVisitingHoursChart receivedData={data.most_active_visiting_hours} />
                    </Grid>
                    <Grid item xs={12}>
                        <TopUsersTable data={data.top_users_by_visits} />
                    </Grid>
                    <Grid item xs={12}>
                        <VisitorStatistics data={data.visitor_statistics} />
                    </Grid>
                    <Grid item xs={12}>
                        <PopularRequirementChart data={data.popular_requirement} />
                    </Grid>
                    <Grid item xs={12}>
                        <UserVisitsByDaysChart data={data.user_visits_by_days} />
                    </Grid>
                    <Grid item xs={12}>
                        <UserVisitsByMonthsChart data={data.user_visits_by_months} />
                    </Grid>
                    <Grid item xs={12}>
                        <LatestFeedbackList data={data.latest_feedback} />
                    </Grid>
                    <Grid item xs={12}>
                        <VisitHistoryList data={data.user_visit_history} />
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
}

export default StatisticsPage;
