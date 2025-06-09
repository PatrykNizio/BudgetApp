import React, { useState, useEffect } from 'react';
import { useQuery } from '@apollo/client';
import { gql } from '@apollo/client';

const GET_USER_BALANCE = gql`
  query GetUserBalance($days: Float) {
    userBalance(days: $days) {
      totalIncome
      totalExpense
      balance
    }
  }
`;

const BalancePage: React.FC = () => {
  const [selectedDays, setSelectedDays] = useState<number | null>(null);
  const { loading, error, data, refetch } = useQuery(GET_USER_BALANCE, {
    variables: { days: selectedDays },
  });

  const handleTimeFilterChange = (days: number | null) => {
    setSelectedDays(days);
  };

  if (loading) return <div>Ładowanie...</div>;
  if (error) return <div>Błąd: {error.message}</div>;

  const balance = data?.userBalance;

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Bilans</h1>
      
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Filtruj po czasie:
        </label>
        <select
          className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
          value={selectedDays || ''}
          onChange={(e) => handleTimeFilterChange(e.target.value ? Number(e.target.value) : null)}
        >
          <option value="">Cały czas</option>
          <option value="7">Ostatni tydzień</option>
          <option value="30">Ostatni miesiąc</option>
          <option value="90">Ostatnie 3 miesiące</option>
          <option value="365">Ostatni rok</option>
        </select>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="bg-white p-4 rounded-lg shadow">
          <h2 className="text-lg font-semibold text-gray-700">Przychody</h2>
          <p className="text-2xl font-bold text-green-600">{balance?.totalIncome.toFixed(2)} PLN</p>
        </div>
        
        <div className="bg-white p-4 rounded-lg shadow">
          <h2 className="text-lg font-semibold text-gray-700">Wydatki</h2>
          <p className="text-2xl font-bold text-red-600">{balance?.totalExpense.toFixed(2)} PLN</p>
        </div>
        
        <div className="bg-white p-4 rounded-lg shadow">
          <h2 className="text-lg font-semibold text-gray-700">Bilans</h2>
          <p className={`text-2xl font-bold ${balance?.balance >= 0 ? 'text-green-600' : 'text-red-600'}`}>
            {balance?.balance.toFixed(2)} PLN
          </p>
        </div>
      </div>
    </div>
  );
};

export default BalancePage; 