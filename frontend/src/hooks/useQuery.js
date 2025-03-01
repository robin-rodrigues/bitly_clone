import { useQuery } from "@tanstack/react-query";
import api from "../api/api";

export const useFetchMyShortUrls = (token, onError) => {
    return useQuery({
        queryKey: ["my-shortenurls"],
        queryFn: async () => {
            const response = await api.get("/api/urls/myurls", {
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        },
        select: (data) => {
            return data.sort(
                (a, b) => new Date(b.createdDate) - new Date(a.createdDate)
            );
        },
        onError,
        staleTime: 5000,
    });
};

export const useFetchTotalClicks = (token, onError) => {
    return useQuery({
        queryKey: ["url-totalclick"],
        queryFn: async () => {
            const response = await api.get(
                "/api/urls/totalClicks?startDate=2024-01-01&endDate=2025-12-31",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            return response.data;
        },
        select: (data) => {
            const convertToArray = Object.keys(data).map((key) => ({
                clickDate: key,
                count: data[key],
            }));

            return convertToArray;
        },
        onError,
        staleTime: 5000,
    });
};
