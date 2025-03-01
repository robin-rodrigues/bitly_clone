import { useQuery } from "@tanstack/react-query";
import api from "../api/api";

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
            return response.data; // Ensure you return `data` directly
        },
        select: (data) => {
            // Since `data` is already `response.data`, access it directly
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
