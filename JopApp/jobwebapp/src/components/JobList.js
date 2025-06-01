import React from 'react';
import JobCard from './JobCard';

const JobList = () => {
  // Dữ liệu mẫu (có thể thay bằng API call)
  const jobs = [
    {
      id: 1,
      company: 'Nhân Viên Bảo Trì Điện Lạnh',
      title: 'Nhân viên phụ vụ quán cà phê',
      description: 'Phục vụ khách, đón đơn đặt bàn, nhận order',
      salaryFrom: 25,
      salaryTo: 30,
      location: '45 Nguyễn Huệ, Q1, HCM',
      workingTime: 'Cười tươi, Tối',
      avatar: 'https://via.placeholder.com/40', // URL ảnh avatar
    },
    {
      id: 2,
      company: 'TechFlex Ltd.',
      title: 'Nhân viên bán hàng thời trang',
      description: 'Tư vấn bán hàng tại cửa hàng quần áo nữ',
      salaryFrom: 27,
      salaryTo: 35,
      location: '88 Trần Hưng Đạo, Q5, HCM',
      workingTime: 'Chiều, Cười tươi',
      avatar: 'https://console.cloudinary.com/app/c-e85a8930706ea6b97e7ca7edffd7c1/assets/media_library/homepage/asset/051dbceaa7f4a87d8840f54bbc1bd8d3/manage/summary?context=manage', // URL ảnh avatar
    },
  ];

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">Danh sách công việc</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {jobs.map((job) => (
          <JobCard key={job.id} job={job} />
        ))}
      </div>
    </div>
  );
};

export default JobList;