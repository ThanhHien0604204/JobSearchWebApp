import React from 'react';

const JobCard = ({ job }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-4 m-2 flex flex-col items-start w-full max-w-sm border border-gray-200">
      <div className="flex items-center mb-2">
        <img
          src={job.avatar || 'https://via.placeholder.com/40'} // Ảnh avatar, dùng placeholder nếu không có
          alt="Avatar"
          className="w-10 h-10 rounded-full mr-2"
        />
        <div>
          <h3 className="text-md font-semibold text-gray-800">{job.company}</h3>
          <p className="text-sm text-gray-600">{job.title}</p>
        </div>
      </div>
      <p className="text-sm text-gray-700 mb-2">{job.description}</p>
      <div className="text-sm text-gray-600 mb-1">
        <span>Lương: {job.salaryFrom} - {job.salaryTo} triệu</span>
      </div>
      <div className="text-sm text-gray-600 mb-1">
        <span>Địa điểm: {job.location}</span>
      </div>
      <div className="text-sm text-gray-600 mb-1">
        <span>Thời gian: {job.workingTime}</span>
      </div>
      <div className="flex justify-between w-full mt-2">
        <button className="text-blue-500 text-sm hover:underline">Xem chi tiết</button>
        <button className="text-green-500 text-sm hover:text-green-700">
          <span role="img" aria-label="like">❤️</span> Yêu thích
        </button>
      </div>
    </div>
  );
};

export default JobCard;