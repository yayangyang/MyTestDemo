package mobiesafe74.itheima.com.sample;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.http2.Http2Connection;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CountingRequestBody extends RequestBody{

    protected  RequestBody delegate;
    private  Listener mListener;

    private  CountingSink countingSink;

    public CountingRequestBody(RequestBody delegate, Listener listener){
        this.delegate=delegate;
        this.mListener=listener;
    }

    public static interface Listener{
        void onRequestProgress(long byteWrited,long contentLength);
    };

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    protected  final class CountingSink extends ForwardingSink{
        private long bytesWriten;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWriten+=byteCount;
            mListener.onRequestProgress(bytesWriten,contentLength());
        }
    }

    @Override
    public void writeTo(BufferedSink sink) {
        countingSink=new CountingSink(sink);
        BufferedSink bufferedSink= Okio.buffer(countingSink);
        try {
            delegate.writeTo(bufferedSink);
            bufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
