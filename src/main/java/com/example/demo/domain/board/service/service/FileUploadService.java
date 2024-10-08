package com.example.demo.domain.board.service.service;

import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.demo.domain.board.Repository.BoardRepository;
import com.example.demo.domain.board.Repository.ImageFileRepository;
import com.example.demo.domain.board.domain.dto.request.FileRequest;
import com.example.demo.domain.board.domain.entity.Board;
import com.example.demo.domain.board.domain.entity.ImageFile;
import com.example.demo.global.base.exception.ErrorCode;
import com.example.demo.global.base.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {
	private final ImageFileRepository imageFileRepository;
	private final BoardRepository boardRepository;


	@Transactional
	public void changeAttachFileUrl(String url, Board board) {
		board.changeAttachFileUrl(url);
		boardRepository.save(board);
	}

	@Transactional
	public void saveImageFileUrl(String url, Board board) {
		ImageFile imageFile = ImageFile.of(url, board);
		boardRepository.save(board);
		imageFileRepository.save(imageFile);
	}

	@Transactional
	public void deleteImageFileUrl(Board board, FileRequest fileRequest) {
		ImageFile imageFile = imageFileRepository.findByUrlAndBoard(fileRequest.getUrl(), board)
			.orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_IMAGE_FILE));
		imageFileRepository.delete(imageFile);
	}
}
